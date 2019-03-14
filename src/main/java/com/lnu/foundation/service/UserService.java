package com.lnu.foundation.service;

import com.lnu.foundation.model.*;
import com.lnu.foundation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by rucsac on 15/10/2018.
 */
@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private TestSessionRepository sessionRepo;

    @Autowired
    private TherapyRepository therapyRepository;

    @Autowired
    private TherapyService therapyService;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private OrganizationRepository orgRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Collection<TestSession> getSessions(String username) {
        return sessionRepo.findByTherapy_Med_Username(username);
    }


    public Collection<TestSession> getSessions() {
        return sessionRepo.findAll();
    }

    public Collection<TestSession> getPatientSessions(String username) {
        return sessionRepo.findByTherapy_Patient_Username(username);
    }

    public List<Therapy> getTherapiesByPatient(String username) {
        return therapyRepository.findByPatient_Username(username);
    }

    public List<Therapy> getTherapiesByMed(String username) {
        return therapyRepository.findByMed_Username(username);
    }


    public List<Therapy> getTherapies() {
        return therapyRepository.findAll();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public User signup(SignupForm signupForm) {
        Role role = this.roleRepo.findByName(signupForm.getRoleName());

        final User user = new User();
        user.setEmail(signupForm.getEmail());
        user.setUsername(signupForm.getUsername());
        user.setFirstName(signupForm.getName());
        if (signupForm.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        }
        user.setProvider(signupForm.getProvider());
        user.setLat(signupForm.getLatitude());
        user.setLongitude(signupForm.getLongitude());
        user.setRole(role);
        if ("PHYSICIAN".equalsIgnoreCase(signupForm.getRoleName())) {
            Organization org = this.orgRepo.getOne(signupForm.getOrganizationId());
            user.addOrganization(org);
        }
        repository.save(user);

        if ("PATIENT".equalsIgnoreCase(signupForm.getRoleName())) {
            this.therapyService.startTherapy(user, signupForm.getOrganizationId());
        }

        return user;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user) {
        repository.save(user);
        return user;
    }


    // Will be called after form based authentication to fetch user details
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        com.lnu.foundation.model.User user = repository.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("LOGGED_USER"));
        //new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities)
        return user;


    }


    public User findUserByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    public User findByRole_Name(String role) {
        return repository.findByRole_Name(role).get(0);
    }

    public List<Duration> getAvaliableWorkingDurations(String username, LocalDate toDate) {
        User user = findUserByUsername(username);
        List<Duration> available = user.getWorkingDates(toDate);
        List<Duration> upcoming = new ArrayList<>();
        Collection<TestSession> upcomingSessions = sessionRepo.findByTherapy_Med_UsernameAndDuration_StartTimeNotNullAndDuration_StartTimeGreaterThan(username, LocalDateTime.now());
        for (TestSession upcomingSession : upcomingSessions) {
            upcoming.add(upcomingSession.getDuration());
        }
        return new DurationsHelper().calculateAvailableDurations(available, upcoming);
    }
}
