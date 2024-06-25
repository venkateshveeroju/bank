package com.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String street;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String country;
    @Column
    private String postalCode;
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL,orphanRemoval = true)
    private Account account;
}
