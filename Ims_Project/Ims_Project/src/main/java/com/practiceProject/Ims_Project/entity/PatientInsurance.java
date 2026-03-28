package com.practiceProject.Ims_Project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.practiceProject.Ims_Project.entity.baseFiles.BaseEntityEMR;
import com.practiceProject.Ims_Project.entity.type.InsurancePriorityEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
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

    @JoinColumn(name = "patient_id", foreignKey =  @ForeignKey(name="fk_patient_insurance_mst_sb_patient_mst_sb"))
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private Patient patient;

    //--------------------------------Foreign Key Columns
    @Column(updatable = false, insertable = false)
    @ToString.Include(name = "patientId")
    @JsonProperty("patientId")
    private Long getPatientId(){
        return patient.getTranId();
    }
}
