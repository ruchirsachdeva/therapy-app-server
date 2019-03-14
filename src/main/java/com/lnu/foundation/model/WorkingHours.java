package com.lnu.foundation.model;


import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static java.time.DayOfWeek.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class WorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workingHoursId;
    @NonNull
    private LocalTime from = LocalTime.of(8, 0);

    @NonNull
    private LocalTime to = LocalTime.of(20, 0);

    @ElementCollection
    @CollectionTable(name = "weekly_days_off", joinColumns = @JoinColumn(name = "working_hours_id"))
    @Column(name = "weekly_days_off")
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> weeklyDaysOff = new HashSet<>(Arrays.asList(SATURDAY, SUNDAY));

    @ElementCollection
    @CollectionTable(name = "other_days_off", joinColumns = @JoinColumn(name = "other_hours_id"))
    @Column(name = "other_days_off")
    private Set<LocalDate> otherDaysOff = new HashSet<>();

    public WorkingHours(LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
    }

    public WorkingHours withWeekendOff() {
        this.weeklyDaysOff.addAll(Arrays.asList(SATURDAY, SUNDAY));
        return this;
    }

    public WorkingHours withOtherDaysOff(LocalDate... dates) {
        this.otherDaysOff.addAll(Arrays.asList(dates));
        return this;
    }


    public List<Duration> getWorkingDates(LocalDate toDate) {
        List<Duration> workingDays = new ArrayList<>();
        LocalDate date = LocalDate.now();
        do {
            boolean isWorkingDay = true;
            for (DayOfWeek dayOfWeek : weeklyDaysOff) {
                if (date.getDayOfWeek().equals(dayOfWeek)) {
                    isWorkingDay = false;
                    break;
                }
            }
            if (isWorkingDay) {
                for (LocalDate dayOff : otherDaysOff) {
                    if (dayOff.compareTo(date) == 0) {
                        isWorkingDay = false;
                        break;
                    }
                }
            }
            if (isWorkingDay) {
                workingDays.add(new Duration(LocalDateTime.of(date, from), LocalDateTime.of(date, to)));
            }
            date = date.plusDays(1);
        } while (toDate.compareTo(date) >= 0);
        return workingDays;
    }


    public static void main(String[] args) {
        WorkingHours wh = new WorkingHours()
                .withOtherDaysOff(LocalDate.of(2019, 3, 15), LocalDate.of(2019, 3, 22), LocalDate.of(2019, 4, 26));
        List<Duration> workingDates = wh.getWorkingDates(LocalDate.of(2019, 4, 26));
        workingDates.forEach(d -> System.out.println(d));

    }


}
