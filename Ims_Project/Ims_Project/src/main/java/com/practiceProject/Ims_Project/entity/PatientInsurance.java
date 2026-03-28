package com.practiceProject.Ims_Project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.practiceProject.Ims_Project.entity.baseFiles.BaseEntityEMR;
import com.practiceProject.Ims_Project.entity.type.InsurancePriorityEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "patient_insurance_mst_sb")
public class PatientInsurance extends BaseEntityEMR {

    @Column(nullable = false)
    private String planName;

    private String code;

    private BigDecimal copay;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private InsurancePriorityEnum priority;

    @ManyToOne
    @JoinColumn(
            name = "patient_id",
            foreignKey =  @ForeignKey(name="fk_patient_insurance_mst_sb_patient_mst_sb"),
            nullable = false
    )
    @ToString.Exclude
    @JsonIgnore
    private Patient patient;


    //--------------------------------Foreign Key Columns
    @JsonProperty("patientId")
    @ToString.Include(name = "patientId")
    @Column(updatable = false,insertable = false)
    private Long getPatientId(){
        return patient.getTranId();
    }
}
