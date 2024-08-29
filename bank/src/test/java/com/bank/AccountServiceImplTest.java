package com.bank;


import com.bank.entity.Account;
import com.bank.entity.Role;
import com.bank.entity.User;
import com.bank.enums.Status;
import com.bank.mapper.AccountMapper;
import com.bank.model.AccountM;
import com.bank.model.NewAccount;
import com.bank.model.UserCreated;
import com.bank.model.UserM;
import com.bank.repository.*;
import com.bank.service.AccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AccountServiceImpl.class)
class AccountServiceImplTest {

    @Autowired
    private AccountServiceImpl accountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private AccountMapper accountMapper;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PrivilegeRepository privilegeRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount_Success() throws JsonProcessingException {
        // Prepare mock data

        String json = "{\n" +
                "  \"User\": {\n" +
                "    \"name\": \"ff\",\n" +
                "    \"email\": \"ff@gmail.com\",\n" +
                "    \"password\": \"test\",\n" +
                "    \n" +
                "    \"address\": {\n" +
                "      \"street\": \"street name\",\n" +
                "      \"city\": \"city name\",\n" +
                "      \"state\": \"state name\",\n" +
                "      \"country\": \"country name\",\n" +
                "      \"postalCode\": \"501510\"\n" +
                "    },\n" +
                "    \"account\": {\n" +
                "      \"accountNumber\": \"A1234\",\n" +
                "      \"balance\": 5000,\n" +
                "      \"status\": \"string\",\n" +
                "      \"createdTimeStamp\": \"2024-07-04T15:34:29.975Z\",\n" +
                "      \"UpdatedTimeStamp\": \"2024-07-04T15:34:29.975Z\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();
        //NewAccount newAccount = new NewAccount();
        NewAccount newAccount = objectMapper.readValue(json, NewAccount.class);

        when(userRepository.findEmailByEmail(newAccount.getUser().getEmail())).thenReturn(null);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(new Role("ROLE_USER"));
        when(accountMapper.convertToUserCreated(any(Account.class))).thenReturn(new UserCreated());
        when(userRepository.save(any(User.class))).thenReturn(new User());
        // Call the method to test
        UserCreated userCreated = accountService.createAccount(newAccount);
        // Verify the result
        assertNotNull(userCreated);
    }

    @Test
    void testCreateAccountUserAlreadyExists() {
        NewAccount newAccount = new NewAccount();
        UserM user = new UserM();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        newAccount.setUser(user);

        when(userRepository.findEmailByEmail(anyString())).thenReturn("john.doe@example.com");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.createAccount(newAccount);
        });

        assertTrue(exception.getMessage().contains("User already exists in system with Email : john.doe@example.com"));
    }

    /*@Test
    public void testConvertToAccountM_WithEmptyOptional() {
        Optional<Account> optionalAccount = Optional.empty();
         accountMapper.convertToAccountM(optionalAccount);
    }*/
    @Test
    public void testGetAccountSuccess() {
        // Arrange
        String accountNumber = "12345";

        // Create a sample Account object
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setStatus(Status.ACTIVE);
        account.setBalance(BigDecimal.valueOf(1000));
        account.setUpdatedTimeStamp(new Date());

        // Create a sample AccountM object
        AccountM accountM = new AccountM();
        accountM.setAccountNumber(accountNumber);
        accountM.setStatus("Active");
        accountM.setBalance(BigDecimal.valueOf(1000));
        accountM.setUpdatedTimeStamp(account.getUpdatedTimeStamp());

        // Mock the repository and mapper behavior
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
        when(accountMapper.convertToAccountM(Optional.of(account))).thenReturn(accountM);

        // Act
        AccountM result = accountService.getAccount(accountNumber);

        // Assert
        assertNotNull(result, "AccountM should not be null");
        assertEquals(accountNumber, result.getAccountNumber(), "Account number should match");
        assertEquals("Active", result.getStatus(), "Status should match");
        assertEquals(BigDecimal.valueOf(1000), result.getBalance(), "Balance should match");
        assertEquals(account.getUpdatedTimeStamp(), result.getUpdatedTimeStamp(), "Updated timestamp should match");

        // Verify interactions
        verify(accountRepository).findByAccountNumber(accountNumber);
        verify(accountMapper).convertToAccountM(Optional.of(account));
    }

    @Test
    public void testGetAccountNotFound() {
        // Arrange
        String accountNumber = "12345";

        // Mock the repository to return an empty Optional
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> accountService.getAccount(accountNumber),
                "Expected getAccount() to throw, but it didn't"
        );

        assertEquals("Account does not exist : " + accountNumber, thrown.getMessage());

        // Verify interactions
        verify(accountRepository).findByAccountNumber(accountNumber);
        verify(accountMapper, never()).convertToAccountM(any());
    }

    //@Test
    void testDepositToAccountSuccess() {
        Account account = new Account();
        account.setAccountNumber("123456");
        account.setBalance(new BigDecimal(1000));
        account.setUpdatedTimeStamp(new Date());
        account.setStatus(Status.ACTIVE);
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));
        when(accountMapper.convertToAccountM(Optional.ofNullable(any(Account.class)))).thenReturn(new AccountM());
        when(accountRepository.findBalanceByAcctID(anyString())).thenReturn(new BigDecimal(1000));
        AccountM accountM = accountService.depositToAccount("123456", new BigDecimal(500));

        assertNotNull(accountM);
        // verify(accountRepository, times(1)).findByAccountNumber(anyString());
        //verify(accountRepository, times(1)).saveBalanceByAcctID(anyString(), any(BigDecimal.class));
    }

    @Test
    void testDepositToAccountNotFound() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.ofNullable(null));
        Account account = new Account();
        account.setAccountNumber("123456");
        account.setBalance(new BigDecimal(1000));
        account.setUpdatedTimeStamp(new Date());
        account.setStatus(Status.ACTIVE);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.depositToAccount("123456", new BigDecimal(500));
        });
        assertEquals("Receiver Account does not exists in our Bank", exception.getMessage());
    }
}