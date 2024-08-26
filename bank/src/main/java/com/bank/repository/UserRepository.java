package com.bank.repository;

import com.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select EMAIL from users where email=:email", nativeQuery = true)
    String findEmailByEmail(@Param("email") String email);

    Optional<User> findByEmail(String email);
}
