package com.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@AllArgsConstructor
public class AuditEntity {
    @CreationTimestamp
    private LocalDateTime createdTimeStamp =  LocalDateTime.now();
    @UpdateTimestamp
    private LocalDateTime updatedTimeStamp;
    @Column
    private String lastModifiedBy;
    @Column
    private String lastUpdatedBy;
    @Transient
    @Column
    private UUID corrId = UUID.randomUUID();
}
