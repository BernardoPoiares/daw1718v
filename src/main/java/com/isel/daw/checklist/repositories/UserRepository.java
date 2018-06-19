package com.isel.daw.checklist.repositories;

import com.isel.daw.checklist.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.jws.soap.SOAPBinding;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);

    @Query(value="SELECT * FROM users WHERE token= :token", nativeQuery = true)
    Users findByToken(@Param("token")String token);
}
