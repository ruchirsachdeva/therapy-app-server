package com.lnu.foundation.repository;

import com.lnu.foundation.model.TestSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by rucsac on 15/10/2018.
 */
@RepositoryRestResource
public interface TestSessionRepository extends JpaRepository<TestSession, Long> {
    @RestResource(path = "byMed", rel = "byMed")
    Collection<TestSession> findByTherapy_Med_Username(@Param("med") String username);

    @RestResource(path = "byPatient", rel = "byPatient")
    Collection<TestSession> findByTherapy_Patient_Username(@Param("patient") String username);

    @RestResource(path = "byMedId", rel = "byMedId")
    Collection<TestSession> findByTherapy_Med_UserId(@Param("id") Long id);

    @RestResource(path = "byPatientId", rel = "byPatientId")
    Collection<TestSession> findByTherapy_Patient_UserId(@Param("id") Long id);

    @RestResource(path = "byTherapyId", rel = "byTherapyId")
    Collection<TestSession> findByTherapy_TherapyId(@Param("id") Long id);

    @RestResource(path = "byTherapyIdRequested", rel = "byTherapyIdRequested")
    Collection<TestSession> findByTherapy_TherapyIdAndDuration_StartTimeIsNull(@Param("id") Long id);

    @RestResource(path = "byTherapyIdUpcoming", rel = "byTherapyIdUpcoming")
    Collection<TestSession> findByTherapy_TherapyIdAndDuration_StartTimeNotNullAndDuration_StartTimeGreaterThan(@Param("id") Long id, @Param("currentDate") LocalDateTime currentDate);

    @RestResource(path = "byTherapyIdHistory", rel = "byTherapyIdHistory")
    Collection<TestSession> findByTherapy_TherapyIdAndDuration_StartTimeNotNullAndDuration_StartTimeLessThan(@Param("id") Long id, @Param("currentDate") LocalDateTime currentDate);

    @RestResource(path = "byMedUpcoming", rel = "byMedUpcoming")
    Collection<TestSession> findByTherapy_Med_UsernameAndDuration_StartTimeNotNullAndDuration_StartTimeGreaterThan(@Param("med") String username, @Param("currentDate") LocalDateTime currentDate);

}
