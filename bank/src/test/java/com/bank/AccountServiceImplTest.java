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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PrivilegeRepository privilegeRepository;

    @Mock
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
        System.out.println(newAccount);
       User user = new User();
        user.setEmail("test@example.com");
        user.setName("John Doe");
        user.setPassword("password");
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Cityville");
        address.setState("State");
        address.setCountry("Country");
        address.setPostalCode("12345");
        user.setAddress(address);
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));
        account.setStatus(Status.ACTIVE);
       // user.setAccount(accountMapper.convertToUserCreated(account));
        UserM userM = new UserM();
        userM.setEmail("test@example.com");
        userM.setName("John Doe");
       // newAccount.setUser(userM);
       // newAccount.setUser(userM);
        // Prepare mock data
        Role role = mock(Role.class);




        Role adminRole = mock(Role.class);
        Privilege privilege = mock(Privilege.class);
        privilege.setName("READ");
        Set<Privilege> privCollection = new HashSet<>();
        privCollection.add(privilege);
        role.setPrivileges(privCollection);
      //  when(roleRepository.findByName("ROLE_USER")).thenReturn(role);
      //  when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(adminRole);

        when(role.getName()).thenReturn("ROLE_USER");
        //when(role.getName()).thenReturn(Collections.emptyList());

        when(adminRole.getName()).thenReturn("ROLE_ADMIN");
        when(adminRole.getPrivileges()).thenReturn(privCollection);

        // Test with non-null strRoles
        Set<String> strRoles = new HashSet<>();
        strRoles.add("ROLE_USER");
        //
        when(userRepository.findEmailByEmail(newAccount.getUser().getEmail())).thenReturn(null);
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(new Role("ROLE_ADMIN"));
        when(roleRepository.findByName("ROLE_USER")).thenReturn(new Role("ROLE_USER"));
        when(accountMapper.convertToUserCreated(any(Account.class))).thenReturn(new UserCreated());

        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(userRepository.save(any(User.class))).thenReturn(user);



        // Call the method to test
        UserCreated userCreated = accountService.createAccount(newAccount);

        // Verify the result
        assertNotNull(userCreated);
        verify(accountRepository).save(any(Account.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testCreateAccountUserExists() {
        NewAccount newAccount = new NewAccount();
        UserM user = new UserM();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        newAccount.setUser(user);

        when(userRepository.findEmailByEmail(anyString())).thenReturn("john.doe@example.com");

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            accountService.createAccount(newAccount);
        });

        assertEquals("Unsuccessfull john.doe@example.com", exception.getMessage());
    }

    @Test
    void testGetAccountSuccess() {
        Account account = new Account();
        account.setAccountNumber("123456");
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(account);
        when(accountMapper.convertToAccountM(any(Account.class))).thenReturn(new AccountM());

        AccountM accountM = accountService.getAccount("123456");

        assertNotNull(accountM);
        verify(accountRepository, times(1)).findByAccountNumber(anyString());
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
        verify(accountRepository, times(1)).findByAccountNumber(anyString());
        verify(accountRepository, times(1)).saveBalanceByAcctID(anyString(), any(BigDecimal.class));
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