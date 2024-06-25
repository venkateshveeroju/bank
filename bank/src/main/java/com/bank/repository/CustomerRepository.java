package com.bank.repository;

import com.bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select Email from customer where EMAIL=:email", nativeQuery = true)
    String customerExistsByEmail(@Param("email") String email);

    @Query(value = "select firstName, lastName, email, dob, account, amount, status from customer where id=:custId", nativeQuery = true)
    boolean findByCustomerId(@Param("custId") BigDecimal custId);

}
