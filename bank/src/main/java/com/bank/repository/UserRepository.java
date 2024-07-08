package com.bank.repository;

import com.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*  @Query(value = "select Email from user where EMAIL=:email", nativeQuery = true)
      String customerExistsByEmail(@Param("email") String email);

      @Query(value = "select DOB,EMAIL,FIRST_NAME,LAST_NAME,PASSWORD from user where id=:userId", nativeQuery = true)
      User findByUserId(Long userId);*/
    @Query(value = "select EMAIL from users where email=:email", nativeQuery = true)
    String findEmailByEmail(@Param("email") String email);

    Optional<User> findByEmail(String email);
}
