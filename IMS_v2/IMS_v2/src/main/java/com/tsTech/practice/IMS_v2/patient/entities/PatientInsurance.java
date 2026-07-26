package com.tsTech.practice.IMS_v2.patient.entities;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import com.tsTech.practice.IMS_v2.patient.enums.InsurancePriority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

////////////////////////////////////////////////
//
// Name: Patient Insurance entity
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 29, 2026 || TaukirS (ER 1005 - patient insurance setup)
// v1.2 || type : Change || Jul 23, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_insurance_sb")
public class PatientInsurance extends BaseEntity {

    //Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes) - added nullable = false for necessary fields
    @Column(nullable = false)
    private String planName;

    @Column(nullable = false)
    private String planCode;

    private Double copay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InsurancePriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, updatable = false)
    private Patient patient;

    @Column(length = 2000)
    private String note;

    private LocalDate expiresAt;
}
