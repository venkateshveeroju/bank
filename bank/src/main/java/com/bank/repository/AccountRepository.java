package com.bank.repository;

import com.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);
    @Query(value = "SELECT * FROM ACCOUNT WHERE account_Number =:accountNumber", nativeQuery = true)
    Account findByAccountNumber(String accountNumber);
}
