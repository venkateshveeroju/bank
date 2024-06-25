package com.bank.entity;

import com.bank.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String dob;
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL,orphanRemoval = true)
    private Account account;

    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_address_id")
    @JoinTable(name = "customer_address_id", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> address = new ArrayList<Address>();
*/
    @Column(nullable = false)
    private BigDecimal accountBalance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date creationDate;
    @UpdateTimestamp
    @Column(nullable = false,updatable = false)
    private Date updationDate;
}
