package com.practiceProject.Ims_Project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.practiceProject.Ims_Project.entity.baseFiles.BaseEntityEMR;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "appointment_mst_sb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Appointment extends BaseEntityEMR {
    private LocalDate apptDate;
    private LocalTime apptTime;

    @Column(length = 1000)
    private String note;

    private Boolean isConfirmed;
    private Boolean isRescheduled;

    private Integer duration;

    @JoinColumn( name = "patient_id", foreignKey = @ForeignKey(name = "fk_appointment_mst_sb_patient_mst_sb") )
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private Patient patient;

    @JoinColumn( name="office_id", foreignKey = @ForeignKey(name = "fk_appointment_mst_sb_office_msg_sb"))
    @ToString.Exclude
    @JsonIgnore
    @OneToOne
    private Office office;

    @JoinColumn(name = "doctor_id", foreignKey = @ForeignKey(name = "fk_appointment_msg_sb_doctor_mst_sb") )
    @ToString.Exclude
    @JsonIgnore
    @OneToOne
    private Doctor doctor;

    //in Future
//    private Case caseId;
//    private Room RoomId;
//    private Procedure procedureId;
//    private VisitType visitTypeId;

    //--------------------------------Foreign Key Showable Columns
    @Column(updatable = false, insertable = false)
    @ToString.Include(name = "officeId")
    @JsonProperty("officeId")
    public Long getOfficeId(){
        return office.getTranId();
    }

    @Column(updatable = false, insertable = false)
    @ToString.Include(name = "doctorId")
    @JsonProperty("doctorId")
    public Long getDoctorId(){
        return doctor.getTranId();
    }

    @Column(updatable = false, insertable = false)
    @ToString.Include(name = "patientId")
    @JsonProperty("patientId")
    public Long getPatientId(){
        return patient.getTranId();
    }
}
