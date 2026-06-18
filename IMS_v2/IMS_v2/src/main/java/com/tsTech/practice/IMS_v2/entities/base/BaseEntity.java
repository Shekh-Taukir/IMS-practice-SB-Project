package com.tsTech.practice.IMS_v2.entities.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jdk.jfr.BooleanFlag;
import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;

////////////////////////////////////////////////
//
// Name: Base Entity class
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
////////////////////////////////////////////////

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tran_id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ColumnDefault(value = "true")
    private Boolean isActive;
}
