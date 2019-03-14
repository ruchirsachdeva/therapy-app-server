package com.lnu.foundation.service;

import com.lnu.foundation.model.Therapy;
import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.TherapyRepository;
import com.lnu.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TherapyService {


    @Autowired
    private TherapyRepository therapyRepository;
    @Autowired
    private UserRepository userRepository;


    public void startTherapy(User patient, Long organizationId) {
        Therapy therapy = new Therapy();
        therapy.setPatient(patient);
        User therapist = findNearestMedByOrganization(patient, organizationId);
        therapy.setMed(therapist);
        this.therapyRepository.save(therapy);
    }

    private User findNearestMedByOrganization(User patient, Long organizationId) {
        List<User> possibleMeds = this.userRepository.findByOrganizations_OrganizationId(organizationId);
        double distance = 0d;
        double minDistance = 1000000d;
        User nearestMed = null;
        for (User possibleMed : possibleMeds) {
            distance = DistanceHelper.distance(possibleMed.getLat(), possibleMed.getLongitude(), patient.getLat(), patient.getLongitude(), null);
            if (distance < minDistance) {
                minDistance = distance;
                nearestMed = possibleMed;
            }
        }
        return nearestMed;
    }

}