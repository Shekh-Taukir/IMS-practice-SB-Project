package com.practiceProject.Ims_Project.dto;

import com.practiceProject.Ims_Project.dto.baseFiles.BaseEntityVIewDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PrescriptionRequestDto {

    @NotNull(message = "Patient is required field")
    private Long patient_id;

    @NotNull(message = "List of prescriptions are required")
    @Valid
    private List<PrescriptionDto> prescriptionList;
}
