package com.lnu.foundation.repository;

import com.lnu.foundation.model.TestSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by rucsac on 15/10/2018.
 */
@RepositoryRestResource(excerptProjection = TestSessionProjection.class)
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

    @RestResource
    @Query("select t from TestSession t left join t.therapy th where th.therapyId = ?1 and t.duration.startTime is null")
    Collection<TestSession> byTherapyIdRequested(@Param("id") Long id);

    @RestResource
    @Query("select t from TestSession t left join t.therapy th where th.therapyId = ?1 and t.duration.startTime is not null and t.duration.startTime >= CURRENT_DATE ")
    Collection<TestSession> byTherapyIdUpcoming(@Param("id") Long id);

    @RestResource
    @Query("select t from TestSession t left join t.therapy th where th.therapyId = ?1 and t.duration.startTime is not null and t.duration.startTime < CURRENT_DATE ")
    Collection<TestSession> byTherapyIdHistory(@Param("id") Long id);

    @RestResource(path = "byMedUpcoming", rel = "byMedUpcoming")
    Collection<TestSession> findByTestSessionIdAndDuration_StartTimeNotNullAndDuration_StartTimeGreaterThan(@Param("testSessionId") long therapyId, @Param("currentDate") LocalDateTime currentDate);

}
