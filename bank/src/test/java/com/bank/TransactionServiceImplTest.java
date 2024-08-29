package com.bank;


import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.entity.User;
import com.bank.mapper.AccountMapper;
import com.bank.model.TransactionM;
import com.bank.model.TransferRequest;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRepository;
import com.bank.security.UserPrinciple;
import com.bank.service.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TransactionServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserPrinciple userPrinciple;
    @InjectMocks
    private TransactionServiceImpl transactionService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWithdrawAmountByAcctID_Success() {
        String accountNumber = "1234567890";
        BigDecimal balance = new BigDecimal("1000.00");
        BigDecimal amount = new BigDecimal("500.00");
        BigDecimal expectedBalance = balance.subtract(amount, new MathContext(10));

        when(accountRepository.findBalanceByAcctID(accountNumber)).thenReturn(balance);

        transactionService.withdrawAmountByAcctID(accountNumber, amount);

        verify(accountRepository, times(1)).withdrawAmountByAcctID(accountNumber, expectedBalance);
    }

    @Test
    void testWithdrawAmountByAcctID_InsufficientBalance() {
        String accountNumber = "1234567890";
        BigDecimal balance = new BigDecimal("100.00");
        BigDecimal amount = new BigDecimal("500.00");

        when(accountRepository.findBalanceByAcctID(accountNumber)).thenReturn(balance);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.withdrawAmountByAcctID(accountNumber, amount);
        });

        assertEquals("Account does not posses sufficient balance to make the transaction. Available balance is : ", exception.getMessage());
        verify(accountRepository, never()).withdrawAmountByAcctID(anyString(), any(BigDecimal.class));
    }

    @Test
    void testSaveBalanceByAcctID_Success() {
        String accountNumber = "1234567890";
        BigDecimal balance = new BigDecimal("1000.00");
        BigDecimal amount = new BigDecimal("500.00");
        BigDecimal expectedBalance = balance.add(amount);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(new Account()));
        when(accountRepository.findBalanceByAcctID(accountNumber)).thenReturn(balance);

        transactionService.saveBalanceByAcctID(accountNumber, amount);

        verify(accountRepository, times(1)).saveBalanceByAcctID(accountNumber, expectedBalance);
    }

    @Test
    void testSaveBalanceByAcctID_AccountDoesNotExist() {
        String accountNumber = "1234567890";
        BigDecimal amount = new BigDecimal("500.00");

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.saveBalanceByAcctID(accountNumber, amount);
        });

        assertEquals("Receiver Account does not exists in our Bank", exception.getMessage());
        verify(accountRepository, never()).saveBalanceByAcctID(anyString(), any(BigDecimal.class));
    }

    //@Test
    @WithMockUser(username = "user", roles = {"ROLE_USER"})
    void testTransfer_Success() {
        TransferRequest request = new TransferRequest();
        request.setSenderAccount("1234567890");
        request.setReceiverAccount("0987654321");
        request.setAmount(new BigDecimal("500.00"));

        Account senderAccount = new Account();
        senderAccount.setId(1L);
        senderAccount.setUser(new User());
        when(accountRepository.findByAccountNumber(request.getSenderAccount())).thenReturn(Optional.of(senderAccount));
        when(accountMapper.convertToTransactionM(any(Transaction.class))).thenReturn(new TransactionM());

        UserPrinciple UserPrinciple = mock(UserPrinciple.class);

        // Mock the chained method calls
       /* when(mockUserPrinciple).thenReturn(mockUserPrinciple);
            when(mockUserPrinciple.builder().build()).thenReturn(mockUserPrinciple);
            when(mockUserPrinciple.getUserId()).thenReturn(1L);
   // Now you can call the method and it should return the mocked user ID
            Long userId = UserPrinciple.builder().build().getUserId();*/
        when(UserPrinciple.builder().build().getUserId()).thenReturn(1L);

        TransactionM transactionM = transactionService.transfer(request);

        assertNotNull(transactionM);
        verify(accountRepository, times(1)).findByAccountNumber(request.getSenderAccount());
    }

    @Test
    void testTransfer_Unauthorized() {
        TransferRequest request = new TransferRequest();
        request.setSenderAccount("1234567890");
        request.setReceiverAccount("0987654321");
        request.setAmount(new BigDecimal("500.00"));

        Account senderAccount = new Account();
        senderAccount.setId(2L); // Different user ID than the one returned by UserPrinciple

        when(accountRepository.findByAccountNumber(request.getSenderAccount())).thenReturn(Optional.of(senderAccount));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transfer(request);
        });

        assertEquals("You are not authorized to make a transfer", exception.getMessage());
    }

    @Test
    void testValidateTransferRequest_ValidRequest() {
        TransferRequest request = new TransferRequest();
        request.setSenderAccount("1234567890");
        request.setReceiverAccount("0987654321");
        request.setAmount(new BigDecimal("500.00"));

        assertDoesNotThrow(() -> transactionService.validateTransferRequest(request));
    }

    @Test
    void testValidateTransferRequest_InvalidRequest_NoSenderAccount() {
        TransferRequest request = new TransferRequest();
        request.setReceiverAccount("0987654321");
        request.setAmount(new BigDecimal("500.00"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.validateTransferRequest(request);
        });

        assertEquals("Sender Account Number cannot be empty ", exception.getMessage());
    }

    @Test
    void testValidateTransferRequest_InvalidRequest_NoReceiverAccount() {
        TransferRequest request = new TransferRequest();
        request.setSenderAccount("1234567890");
        request.setAmount(new BigDecimal("500.00"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.validateTransferRequest(request);
        });

        assertEquals("Receiver Account Number cannot be empty ", exception.getMessage());
    }

    @Test
    void testValidateTransferRequest_InvalidRequest_NoAmount() {
        TransferRequest request = new TransferRequest();
        request.setSenderAccount("1234567890");
        request.setReceiverAccount("0987654321");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.validateTransferRequest(request);
        });

        assertEquals("Amount cannot be empty ", exception.getMessage());
    }
}
