package com.lnu.foundation.repository;

import com.lnu.foundation.model.*;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "testSessionProjection", types = {TestSession.class})
public interface TestSessionProjection {

    Long getTestSessionId();

    TherapyProjection getTherapy();

    List<Note> getNotes();

    Duration getDuration();

    int getRequestedHours();

}
