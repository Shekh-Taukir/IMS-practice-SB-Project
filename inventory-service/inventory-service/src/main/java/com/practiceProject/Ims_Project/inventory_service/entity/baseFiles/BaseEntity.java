package com.practiceProject.Ims_Project.inventory_service.entity.baseFiles;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

////////////////////////////////////////////////
//
 // Name: Base Entity
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Apr 02, 2026 || TaukirS (ER 1110 - Drug entity implementation in inventory_service)
////////////////////////////////////////////////

@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //<- this strategy works like autogenerate system in DB.
    @Column(updatable = false)
    private Long tranId;

    private Boolean isActive;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime changedAt;
}
