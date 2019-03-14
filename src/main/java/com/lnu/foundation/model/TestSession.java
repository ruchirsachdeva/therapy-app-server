package com.lnu.foundation.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TestSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testSessionId;
    private long testType;

    @ManyToOne
    @JoinColumn(name = "therapy_id")
    private Therapy therapy;

    @OneToMany(mappedBy = "testSession", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();


    @Embedded
    private Duration duration;
    private int requestedHours;


    public void addNote(Note note) {
        this.notes.add(note);
    }
}
