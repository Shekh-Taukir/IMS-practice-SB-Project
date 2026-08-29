package com.tsTech.practice.IMS_v2.visitNote.diagnosis.entity;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import com.tsTech.practice.IMS_v2.visitNote.core.entities.VisitNote;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: Diagnosis Entity
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 20, 2026 || TaukirS (ER 1015 - visitnote entity coding)
/////////////////////////////////////////////

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "diagnosis_mst",
        indexes = {
                @Index(name = "idx_diagnosis_mst_patient_id_desc", columnList = "patient_id DESC"),
                @Index(name = "idx_diagnosis_mst_pn_id_desc", columnList = "pn_id DESC")
        }
)
public class Diagnosis extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pn_id", nullable = false)
    private VisitNote visitNote;

    @Column(nullable = false)
    private LocalDate takenAt = LocalDate.now();

    @Column(length = 1000)
    private String note;
}
