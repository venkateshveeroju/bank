package com.bank;


import com.bank.entity.*;
import com.bank.enums.Status;
import com.bank.mapper.AccountMapper;
import com.bank.model.*;
import com.bank.repository.*;
import com.bank.service.AccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes  = AccountServiceImpl.class)
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
    void createAccount_Success() throws JsonProcessingException {
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

    @Test
    void testGetAccountSuccess() {
        Account account = new Account();
        account.setAccountNumber("123456");
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(account);
        when(accountMapper.convertToAccountM(any(Account.class))).thenReturn(new AccountM());

        AccountM accountM = accountService.getAccount("123456");

        assertNotNull(accountM);
        //verify(accountRepository, times(1)).findByAccountNumber(anyString());
    }

@Test
    void testGetAccountNotFound() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.getAccount("123456");
        });

        assertEquals("Account does not exist : 123456", exception.getMessage());
    }

    @Test
    void testDepositToAccountSuccess() {
        Account account = new Account();
        account.setAccountNumber("123456");
        account.setBalance(new BigDecimal(1000));
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(account);
        when(accountMapper.convertToAccountM(any(Account.class))).thenReturn(new AccountM());

        AccountM accountM = accountService.depositToAccount("123456", new BigDecimal(500));

        assertNotNull(accountM);
       // verify(accountRepository, times(1)).findByAccountNumber(anyString());
        //verify(accountRepository, times(1)).saveBalanceByAcctID(anyString(), any(BigDecimal.class));
    }

    @Test
    void testDepositToAccountNotFound() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.depositToAccount("123456", new BigDecimal(500));
        });

        assertEquals("Account does not exist : 123456", exception.getMessage());
    }
}