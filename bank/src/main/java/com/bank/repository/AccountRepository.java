package com.bank.repository;

import com.bank.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);
    @Query(value = "SELECT * FROM ACCOUNT WHERE ACCOUNT_NUMBER =:accountNumber", nativeQuery = true)
    Account findByAccountNumber(String accountNumber);


    @Query(value = "SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NUMBER =:accountNumber", nativeQuery = true)
    BigDecimal findBalanceByAcctID(String accountNumber);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Account set balance = :balance where ACCOUNT_NUMBER=:accountNumber", nativeQuery = true)
    void saveBalanceByAcctID(String accountNumber, BigDecimal balance);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Account set balance = :balance where ACCOUNT_NUMBER=:accountNumber", nativeQuery = true)
    void withdrawAmountByAcctID(String accountNumber, BigDecimal balance);

    String deleteByAccountNumber(String accountNumber);


}
