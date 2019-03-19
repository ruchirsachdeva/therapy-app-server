package com.lnu.foundation.model;


import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Setter
public class Duration {

    private LocalDateTime startTime;
    private LocalDateTime endTime;


}
