package com.tsTech.practice.IMS_v2.setup.entities;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: Visit Type Entity
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 15, 2026 || TaukirS (ER 1012 - visit type entity code)
/////////////////////////////////////////////

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "icd_mst",
        indexes = {@Index(name = "idx_icd_mst_code_is_active", columnList = "code, isActive", unique = true)}
)
public class ICD extends BaseEntity {
    @Column(nullable = false, length = 20, unique = true)
    private String code;

    @Column(length = 500)
    private String description;

    @Column(length = 1000)
    private String note;

    private LocalDate expiredAt;
}
