package com.lnu.foundation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(value = {"users", "organizations"}, ignoreUnknown = true)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_organization",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    @JsonIgnore
    private Set<Organization> organizations = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "working_hours_id")
    private WorkingHours workingHours;

    private Double lat;
    @Column(name = "`long`")
    private Double longitude;

    private String firstName;
    private String lastName;
    private String password;
    private String passwordConfirm;
    private String provider;
    private String image;

    public void addOrganization(Organization organization) {
        organizations.add(organization);
        organization.getUsers().add(this);
    }

    public void removeOrganization(Organization organization) {
        organizations.remove(organization);
        organization.getUsers().remove(this);
    }


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> getRole() != null ? addRolePrefixWithUnderscore() : "ROLE_USER");
    }

    private String addRolePrefixWithUnderscore() {
        return "ROLE_" + getRole().getName().replaceAll(" ", "_").toUpperCase();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @JsonProperty
    public List<Duration> getWorkingDates(LocalDate toDate) {
        List<Duration> workingDates = getNonNullWorkingHours().getWorkingDates(toDate);
        return workingDates;
    }

    private WorkingHours getNonNullWorkingHours() {
        return this.workingHours == null ? new WorkingHours() :
                this.workingHours;
    }

    public String toString() {
        return "User(userId=" + this.getUserId() + ", username=" + this.getUsername() + ", email=" + this.getEmail() + ", role=" + this.getRole() + ", workingHours=" + this.getWorkingHours() + ", lat=" + this.getLat() + ", longitude=" + this.getLongitude() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", password=" + this.getPassword() + ", passwordConfirm=" + this.getPasswordConfirm() + ", provider=" + this.getProvider() + ", image=" + this.getImage() + ")";
    }
}
