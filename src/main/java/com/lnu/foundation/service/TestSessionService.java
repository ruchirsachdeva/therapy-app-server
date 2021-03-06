package com.lnu.foundation.service;

import com.lnu.foundation.model.Duration;
import com.lnu.foundation.model.TestSession;
import com.lnu.foundation.model.Therapy;
import com.lnu.foundation.repository.TestSessionRepository;
import com.lnu.foundation.repository.TherapyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by rucsac on 04/11/2018.
 */
@Service
@Transactional
public class TestSessionService {


    @Autowired
    private TestSessionRepository sessionRepo;


    @Autowired
    private TherapyRepository therapyRepository;


    public void requestSession(long therapyId, int requestedHours) {
        Therapy therapy = therapyRepository.getOne(therapyId);
        TestSession session = new TestSession();
        session.setTherapy(therapy);
        session.setTestType(1);
        session.setRequestedHours(requestedHours);
        sessionRepo.save(session);
    }

    public void bookSession(long sessionId, Duration duration) {
        TestSession session = sessionRepo.getOne(sessionId);
        session.setDuration(duration);
        sessionRepo.save(session);
    }


    public void endSession(Long sessionId) {
        TestSession session = sessionRepo.getOne(sessionId);
        Duration duration = session.getDuration();
        LocalDateTime now = LocalDateTime.now();
        if(duration.getStartTime()!=null && duration.getStartTime().isAfter(now)){
        duration.setStartTime(now);
        }
        duration.setEndTime(now);
        session.setDuration(duration);
        sessionRepo.save(session);

    }
}
