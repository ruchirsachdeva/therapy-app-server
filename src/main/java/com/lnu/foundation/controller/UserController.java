package com.lnu.foundation.controller;

import com.lnu.foundation.model.*;
import com.lnu.foundation.service.NoteService;
import com.lnu.foundation.service.SecurityContextService;
import com.lnu.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Created by rucsac on 10/10/2018.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService service;
    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private NoteService noteService;


    @CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
    @GetMapping("/me/tests")
    public Collection<TestSession> getTestSessions() {
        Collection<TestSession> sessions;
        User user = securityContextService.currentUser().orElseThrow(RuntimeException::new);
        if (user.getRole() != null && "researcher".equals(user.getRole().getName())) {
            sessions = service.getSessions(user.getUsername());
        } else {
            sessions = service.getSessions();
        }
        return sessions;
    }

    @CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
    @GetMapping("user/{username}/tests")
    public Collection<TestSession> getPatientTestSessions(@PathVariable String username) {
        return service.getPatientSessions(username);
    }

    @CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
    @GetMapping("user/{username}")
    public User getUser(@PathVariable String username) {
        return service.findUserByUsername(username);
    }

    @CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
    @GetMapping("/me")
    public User getMe() {
        return securityContextService.currentUser().orElseThrow(RuntimeException::new);
    }

    @CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
    @PostMapping("user/me/tests/{testSessionId}/note")
    public Collection<Note> addNote(@PathVariable Long testSessionId, @RequestBody Note note) {
        User user = securityContextService.currentUser().orElseThrow(RuntimeException::new);
        return noteService.addNote(testSessionId, note, user);
    }


    @CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
    @GetMapping("/therapies")
    public Collection<Therapy> getTherapies() {
        Collection<Therapy> therapies = null;
        User user = securityContextService.currentUser().orElseThrow(RuntimeException::new);
        if ("physician".equals(user.getRole().getName())) {
            therapies = service.getTherapiesByMed(user.getUsername());
        } else if ("researcher".equals(user.getRole().getName())
                || "junior researcher".equals(user.getRole().getName())) {
            therapies = service.getTherapies();
        } else {
            therapies = service.getTherapiesByPatient(user.getUsername());
        }

        return therapies;
    }

    @CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
    @GetMapping("/hours/{sessionId}")
    public List<Duration> getAvailableWorkingDuration(@PathVariable("sessionId") Long sessionId) {
        LocalDate of = LocalDate.of(2019, 4, 13);
        return service.getAvailableWorkingDurations(sessionId);
    }


}
