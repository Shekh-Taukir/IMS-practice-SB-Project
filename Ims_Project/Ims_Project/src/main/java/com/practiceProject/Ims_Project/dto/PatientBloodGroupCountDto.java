package com.practiceProject.Ims_Project.dto;

import com.practiceProject.Ims_Project.entity.type.BloodGroupEnum;
import com.practiceProject.Ims_Project.entity.type.UserPrefixEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PatientBloodGroupCountDto {
    private final BloodGroupEnum bloodGroup;
    private final Long patientCount;
}
