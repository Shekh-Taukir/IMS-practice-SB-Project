package com.tsTech.practice.IMS_v2.visitNote.diagnosis.entity;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import com.tsTech.practice.IMS_v2.setup.icd.entities.ICD;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/// //////////////////////////////////////////
//
// Name: Diagnosis ICD Mapping Entity
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)

/// //////////////////////////////////////////

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "diagnosis_icd_mapping",
        indexes = {
                @Index(name = "idx_diagnosis_icd_mapping_diagnosis_id_icd_id", columnList = "diagnosis_id, icd_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_diagnosis_icd_mapping_diagnosis_id_seq", columnNames = {"diagnosis_id", "seq"})
        }
)
public class DiagnosisIcdMap extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Diagnosis diagnosis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icd_id", nullable = false)
    private ICD icd;

    @Column(nullable = false, columnDefinition = "BIGINT CHECK (seq BETWEEN 1 AND 8)")
    private Long seq;

    public String toString() {
        return "DiagnosisIcdMap{diagnosis : " + diagnosis.getTranId() + " | icd: " + icd.getTranId() + " | seq = " + seq;
    }
}
