package com.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
@MappedSuperclass
public class AuditEntity {
    @CreationTimestamp
    private LocalDateTime createdTimeStamp =  LocalDateTime.now();
    @UpdateTimestamp
    private Date UpdatedTimeStamp;
    @Column
    private String lastModifiedBy;
    @Column
    private String lastUpdatedBy;
    @Transient
    @Column
    private UUID randomUUId = UUID.randomUUID();
}
