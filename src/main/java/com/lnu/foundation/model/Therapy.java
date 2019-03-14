package com.lnu.foundation.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Therapy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long therapyId;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;
    @ManyToOne
    @JoinColumn(name = "med_id")
    private User med;

    @Embedded
    private Duration duration;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;


}
