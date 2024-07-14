package com.bank.entity;

import com.bank.enums.AccountType;
import com.bank.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatusCode;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private String accountNumber;

    private BigDecimal balance;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @CreationTimestamp
    private Date createdTimeStamp;
    @UpdateTimestamp
    private Date UpdatedTimeStamp;
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
