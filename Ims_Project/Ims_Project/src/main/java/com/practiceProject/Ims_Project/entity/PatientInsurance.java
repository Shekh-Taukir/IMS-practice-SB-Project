package com.practiceProject.Ims_Project.entity;

import com.practiceProject.Ims_Project.entity.type.InsurancePriorityEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patient_insurance_mst_sb")
@Builder
@Entity
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
    private Patient patient;
}
