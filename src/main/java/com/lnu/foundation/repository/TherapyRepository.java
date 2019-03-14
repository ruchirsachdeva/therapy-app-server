package com.lnu.foundation.repository;

import com.lnu.foundation.model.Therapy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;
import java.util.List;

/**
 * Created by rucsac on 15/10/2018.
 */
@RepositoryRestResource
public interface TherapyRepository extends JpaRepository<Therapy, Long> {

    @RestResource(path = "byMed", rel = "byMed")
    List<Therapy> findByMed_Username(@Param("med") String med);

    @RestResource(path = "byPatient", rel = "byPatient")
    List<Therapy> findByPatient_Username(@Param("patient") String patient);

    @RestResource(path = "byMedOngoing", rel = "byMedOngoing")
    Collection<Therapy> findByMed_UsernameAndDuration_StartTimeNotNullAndDuration_EndTimeIsNull(@Param("med") String med);

    @RestResource(path = "byPatientOngoing", rel = "byPatientOngoing")
    Collection<Therapy> findByPatient_UsernameAndDuration_StartTimeNotNullAndDuration_EndTimeIsNull(@Param("patient") String patient);

    @RestResource(path = "byMedHistory", rel = "byMedHistory")
    Collection<Therapy> findByMed_UsernameAndDuration_StartTimeNotNullAndDuration_EndTimeNotNull(@Param("med") String med);

    @RestResource(path = "byPatientHistory", rel = "byPatientHistory")
    Collection<Therapy> findByPatient_UsernameAndDuration_StartTimeNotNullAndDuration_EndTimeNotNull(@Param("patient") String patient);
}
