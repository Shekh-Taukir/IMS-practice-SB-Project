package com.practiceProject.Ims_Project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointment_mst_sb")
public class Appointment extends BaseEntityEMR{
    private LocalDate apptDate;
    private LocalTime apptTime;

    @Column(length = 1000)
    private String note;

    private Boolean isConfirmed;
    private Boolean isRescheduled;

    private Integer duration;

    @ManyToOne
    @JoinColumn(
            name = "patient_id",
            foreignKey = @ForeignKey(name = "fk_appointment_mst_sb_patient_mst_sb"),
            nullable = false
    )
    private Patient patient;

    @OneToOne
    @JoinColumn(
            name="office_id",
            foreignKey = @ForeignKey(name = "fk_appointment_mst_sb_office_msg_sb"),
            nullable = false
    )
    private Office office;

    @OneToOne
    @JoinColumn(
            name = "doctor_id",
            foreignKey = @ForeignKey(name = "fk_appointment_msg_sb_doctor_mst_sb")
    )
    private Doctor doctor;

    //in Future
//    private Case caseId;
//    private Room RoomId;
//    private Procedure procedureId;
//    private VisitType visitTypeId;

}
