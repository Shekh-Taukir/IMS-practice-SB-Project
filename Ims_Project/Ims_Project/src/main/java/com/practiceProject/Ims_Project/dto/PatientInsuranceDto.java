package com.practiceProject.Ims_Project.dto;

import com.practiceProject.Ims_Project.dto.baseFiles.BaseEntityVIewDto;
import com.practiceProject.Ims_Project.entity.type.InsurancePriorityEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientInsuranceDto extends BaseEntityVIewDto {
    private String planName;

    private String code;
    private BigDecimal copay;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private InsurancePriorityEnum priority;

    @Column(updatable = false)
    private Long patientId;
}
