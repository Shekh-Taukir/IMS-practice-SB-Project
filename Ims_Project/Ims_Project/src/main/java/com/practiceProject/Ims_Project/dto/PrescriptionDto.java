package com.practiceProject.Ims_Project.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.practiceProject.Ims_Project.dto.baseFiles.BaseEntityVIewDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonPropertyOrder(value = {"tranId", "drug_id", "drug_name"})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrescriptionDto extends BaseEntityVIewDto {
    @NotNull(message = "intake_dose field is required")
    private Double doseIntake;

    @NotEmpty(message = "sig field is required")
    private String sig;

    @NotNull(message = "days_to_take field is required")
    private Integer daysToTake;

    @NotNull(message = "Drug is required field")
    private Long drug_id;

    @NotNull(message = "prescription date is a required field")
    private LocalDate date;

    private Long test_data;

    private String drug_name;
}
