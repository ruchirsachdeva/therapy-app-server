package com.lnu.foundation.repository;

import com.lnu.foundation.model.Duration;
import com.lnu.foundation.model.Organization;
import com.lnu.foundation.model.Therapy;
import com.lnu.foundation.model.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "therapyProjection", types = {Therapy.class})
public interface TherapyProjection {


    Long getTherapyId();

    User getMed();

    User getPatient();

    Duration getDuration();

    Organization getOrganization();

}
