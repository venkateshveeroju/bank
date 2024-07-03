package com.bank.repository;

import com.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  /*  @Query(value = "select Email from User where EMAIL=:email", nativeQuery = true)
    String UserExistsByEmail(@Param("email") String email);

    @Query(value = "select DOB,EMAIL,FIRST_NAME,LAST_NAME,PASSWORD from User where id=:custId", nativeQuery = true)
    User findByUserId(Long custId);*/
    @Query(value = "select Email from User where EMAIL=:email", nativeQuery = true)
    String findByEmail(@Param("email") String email);

}
