package com.practiceProject.Ims_Project.dto;

import com.practiceProject.Ims_Project.dto.subClasses.AppointmentDoctorDto;
import com.practiceProject.Ims_Project.dto.subClasses.AppointmentOfficeDto;
import com.practiceProject.Ims_Project.dto.subClasses.AppointmentPatientDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto extends AppointmentDto {
    private AppointmentPatientDto patient;
    private AppointmentOfficeDto office;
    private AppointmentDoctorDto doctor;
}
