package com.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column
    private Long userId;
    @Column
    private String senderAccount;
    @Column
    private String receiverAccount;
    @CreationTimestamp
    private Date createdTimeStamp;
    @UpdateTimestamp
    private Date UpdatedTimeStamp;
    @Column
    private String lastModifiedBy;
    @Column
    private String lastUpdatedBy;
    //@Transient
    @Column
    private UUID randomUUId = UUID.randomUUID();

}
