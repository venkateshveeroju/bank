package com.bank.entity;


import com.bank.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String accountNumber;

    private BigDecimal balance;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
   /* @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL,orphanRemoval = true)

    private List<Transaction> transactionList;*/
}
