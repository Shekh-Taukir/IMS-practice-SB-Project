package com.tsTech.practice.IMS_v2.common.entity.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
// v1.2 || type : Change || Jul 23, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {

    //Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    // TODO:    1. need to add Auditable entity fields in Base entity
    //          2. need to createdBy and UpdatedBy field with static value
    //          3. when spring security is added, then need to set actual createdBy and UpdatedBY fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tranId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ColumnDefault(value = "true")
    @JsonProperty("isActive")
    @Column(nullable = false)
    private Boolean isActive=true;
}
