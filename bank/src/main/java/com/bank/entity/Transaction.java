package com.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Transaction  {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  Long id;
    @Column(nullable = false)
    private BigDecimal amount;
    /*@ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")*/
    @Column
    private String senderAccount;
    @Column
    private Long userId;
    @Column
    private String receiverAccount;
    @CreationTimestamp
    private LocalDateTime createdTimeStamp;
    @UpdateTimestamp
    private LocalDateTime UpdatedTimeStamp;
    @Column
    private String lastModifiedBy;
    @Column
    private String lastUpdatedBy;
    //@Transient
    @Column
    private UUID randomUUId = UUID.randomUUID();

}
