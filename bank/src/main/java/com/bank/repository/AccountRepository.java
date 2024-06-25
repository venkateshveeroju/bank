package com.bank.repository;

import com.bank.entity.Account;
import com.bank.model.AccountM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);
    @Query(value = "SELECT * FROM ACCOUNT WHERE ACCOUNT_NUMBER =:accountNumber", nativeQuery = true)
    Account findByAccountNumber(String accountNumber);

}
