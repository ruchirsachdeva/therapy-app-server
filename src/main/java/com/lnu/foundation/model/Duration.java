package com.lnu.foundation.model;


import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.DayOfWeek.SUNDAY;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Duration {

    private LocalDateTime startTime;
    private LocalDateTime endTime;


}
