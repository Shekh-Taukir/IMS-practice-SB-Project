package com.practiceProject.Ims_Project.service;

import com.practiceProject.Ims_Project.dto.PatientInsuranceDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface PatientInsuranceService {
    List<PatientInsuranceDto> getPatientInsuranceList();
}
