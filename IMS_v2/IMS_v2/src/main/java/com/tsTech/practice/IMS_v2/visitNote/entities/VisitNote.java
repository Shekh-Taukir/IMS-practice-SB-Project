package com.tsTech.practice.IMS_v2.visitNote.entities;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import com.tsTech.practice.IMS_v2.office.entities.Office;
import com.tsTech.practice.IMS_v2.office.entities.Provider;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import com.tsTech.practice.IMS_v2.setup.entities.VisitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: Visit Note Entity
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 20, 2026 || TaukirS (ER 1015 - visit note entity coding)
/////////////////////////////////////////////

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "visit_note",
        indexes = {
                @Index(name = "idx_visit_note_patient_id", columnList = "patient_id"),
                @Index(name="idx_visit_note_office_id_provider_id", columnList = "office_id, provider_id")
        }
)
public class VisitNote extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    private Boolean isBillable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_type_id", nullable = false)
    private VisitType visitType;

    private LocalDate encounterDate;

    @Column(length = 200)
    private String procedure;

    @Column(length = 1000)
    private String note;
}
