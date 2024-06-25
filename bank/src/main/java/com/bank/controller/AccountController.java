package com.bank.controller;





import com.bank.api.AccountsApi;
import com.bank.entity.Account;
import com.bank.model.AccountM;
import com.bank.model.CustomerM;
import com.bank.model.DepositRequest;
import com.bank.model.NewAccount;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.service.AccountServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;




@RestController
@RequestMapping("/api")
public class AccountController implements AccountsApi {
    @Autowired
    AccountServiceImpl accountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @GetMapping("/test/{accountNumber}/balance")
    public ResponseEntity<AccountM>  testMethod(@PathVariable String accountNumber){

        Account account = accountRepository.findByAccountNumber(accountNumber);
        if(account == null){
            ResponseEntity.notFound();
        }
        AccountM accountM = new AccountM();
        accountM.setAccountBalance(account.getAccountBalance());
        //accountM.setAccountType(AccountM.AccountTypeEnum.valueOf(account.getAccountType().toString()));

        accountM.setStatus(account.getStatus().toString());
        CustomerM customerM = new CustomerM();
        accountM.setCustomer(customerM);
        accountM.setDateCreated(account.getDateCreated());
        accountM.setLastActivity(account.getLastActivity());
        accountM.setCustomerName(account.getCustomer().getFirstName() + "," + account.getCustomer().getLastName());


        return ResponseEntity.ok(accountM);
    }

    @RequestMapping("/docs/index.html")
    public void apiDocumentation(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @Override
    public ResponseEntity<String> accountId(NewAccount body) {

        return ResponseEntity.ok(accountService.createAccount(body).getBody());
    }

    @Override
    public ResponseEntity<AccountM> accountsAccountIdBalanceGet(String accountId) {

        return null;
    }

    @Override
    public ResponseEntity<Void> depositToAccount(DepositRequest body) {
        return null;
    }
}
