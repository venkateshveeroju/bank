package com.bank.entity;

import com.bank.enums.AccountType;
import com.bank.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatusCode;


import java.math.BigDecimal;
import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal accountBalance;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    /*@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;*/
    @Column(nullable = false)
    private String accountNumber;
    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id",referencedColumnName = "id")
    private Customer customer;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name="address_id",referencedColumnName = "id")
    private Address address;
    @CreationTimestamp
    private Date dateCreated;
    @UpdateTimestamp
    private Date lastActivity;
}
