package com.practiceProject.Ims_Project.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

////////////////////////////////////////////////
//
 // Name: Prescription Request DTO
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : New Func || Apr 02, 2026 || TaukirS (ER 1109 - implement drug module in ims_project)
////////////////////////////////////////////////

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
