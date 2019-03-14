package com.lnu.foundation.repository;

import com.lnu.foundation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

/**
 * Created by rucsac on 10/10/2018.
 */
@RepositoryRestResource
//@CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole_Name(@Param("role") String role);

    @RestResource
    Optional<User> findByUsername(@Param("username") String username);

    @RestResource
    List<User> findByRole_NameAndOrganizations_organizationId(@Param("role") String role, @Param("organizationId") Long organizationId);
}
