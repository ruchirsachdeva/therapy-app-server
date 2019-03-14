package com.lnu.foundation.service;

import com.lnu.foundation.model.Duration;
import com.lnu.foundation.model.WorkingHours;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains helper method(s) to check
 * overlapping date intervals.
 */
public class DurationsHelper {
    /**
     * holds a list of distinct date intervals
     */
    private List<Duration> distinctIntervals;
    private List<Duration> newAvailableDuration = new ArrayList<>();

    public DurationsHelper() {
        distinctIntervals = new ArrayList<>();
    }


    public List<Duration> calculateAvailableDurations(List<Duration> workinghours, List<Duration> bookedDurations) {
        List<Duration> availableDurations = new ArrayList<>();

        for (Duration workinghour : workinghours) {
            boolean isDayBooked = false;
            List<Duration> daysAvailableDurations = new ArrayList<>();
            for (Duration booked : bookedDurations) {

                if (workinghour.getStartTime().getDayOfYear() == booked.getStartTime().getDayOfYear()) {
                    if (!isDayBooked) {
                        isDayBooked = true;
                        daysAvailableDurations.addAll(getAvailableDurations(booked, workinghour));
                    } else {
                        for (Duration availableDuration : new ArrayList<>(daysAvailableDurations)) {
                            if (overlaps(availableDuration, booked)) {
                                daysAvailableDurations.remove(availableDuration);
                                daysAvailableDurations.addAll(getAvailableDurations(booked, availableDuration));
                            }
                        }
                    }
                }
            }
            if (!isDayBooked) {
                daysAvailableDurations.add(workinghour);
            }
            availableDurations.addAll(daysAvailableDurations);
        }

        for (Duration duration : availableDurations) {
            System.out.println(duration);
        }
        return availableDurations;
    }


    /**
     * This method flags whether a given two date intervals
     * overlap.
     *
     * @param left
     * @param right
     * @return true, if overlaps & false, otherwise.
     */
    private boolean overlaps(Duration left, Duration right) {
        boolean result = true;
        if (left.getEndTime().isBefore(right.getStartTime())
                && left.getEndTime().isBefore(right.getEndTime())) {
            result = false;
        }
        if (left.getStartTime().isAfter(right.getStartTime())
                && left.getStartTime().isAfter(right.getEndTime())) {
            result = false;
        }
        return result;
    }


    private List<Duration> getAvailableDurations(Duration booked, Duration duration) {
        List<Duration> availableDurations = new ArrayList<>();
        if (duration.getStartTime().isBefore(booked.getStartTime())) {
            availableDurations.add(new Duration(duration.getStartTime(), booked.getStartTime()));
        }
        if (booked.getEndTime().isBefore(duration.getEndTime())) {
            availableDurations.add(new Duration(booked.getEndTime(), duration.getEndTime()));
        }
        return availableDurations;
    }

    private static LocalDateTime getDuration(int year, int month, int dayOfMonth, int hour, int minute) {
        return LocalDateTime.of(LocalDate.of(year,
                month, dayOfMonth), LocalTime.of(hour, minute));
    }

    public static void main(String[] args) { // Command+Alt+6

        WorkingHours wh = new WorkingHours()
                .withOtherDaysOff(LocalDate.of(2019, 3, 15), LocalDate.of(2019, 3, 22), LocalDate.of(2019, 4, 26));
        List<Duration> workingDates = wh.getWorkingDates(LocalDate.of(2019, 4, 26));
        // workingDates.forEach(d -> System.out.println(d));


        LocalDateTime upcomingFrom1 = getDuration(2019, 03, 22, 9, 0);
        LocalDateTime upcomingTo1 = getDuration(2019, 03, 22, 12, 0);

        LocalDateTime upcomingFrom2 = getDuration(2019, 03, 27, 10, 0);
        LocalDateTime upcomingTo2 = getDuration(2019, 03, 27, 16, 0);


        LocalDateTime upcomingFrom3 = getDuration(2019, 04, 01, 14, 0);
        LocalDateTime upcomingTo3 = getDuration(2019, 04, 01, 17, 0);

        LocalDateTime upcomingFrom4 = getDuration(2019, 04, 17, 10, 0);
        LocalDateTime upcomingTo4 = getDuration(2019, 04, 17, 11, 0);


        LocalDateTime upcomingFrom5 = getDuration(2019, 04, 17, 11, 0);
        LocalDateTime upcomingTo5 = getDuration(2019, 04, 17, 12, 0);

        List<Duration> upcomingDates = new ArrayList<>();

        upcomingDates.add(new Duration(upcomingFrom1, upcomingTo1));
        upcomingDates.add(new Duration(upcomingFrom2, upcomingTo2));
        upcomingDates.add(new Duration(upcomingFrom3, upcomingTo3));
        upcomingDates.add(new Duration(upcomingFrom4, upcomingTo4));
        upcomingDates.add(new Duration(upcomingFrom5, upcomingTo5));

        DurationsHelper ceo = new DurationsHelper();
        ceo.calculateAvailableDurations(workingDates, upcomingDates);
    }
}