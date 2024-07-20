package com.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditEntity {
    @CreationTimestamp
    private Date createdTimeStamp = Date.from(Instant.now());
    @UpdateTimestamp
    private Date updatedTimeStamp;
    @Column
    private String lastModifiedBy;
    @Column
    private String lastUpdatedBy;
    @Transient
    @Column
    private UUID corrId = UUID.randomUUID();
}
