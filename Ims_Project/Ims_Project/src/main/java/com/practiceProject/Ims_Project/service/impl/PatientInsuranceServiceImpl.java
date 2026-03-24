package com.practiceProject.Ims_Project.service.impl;

import com.practiceProject.Ims_Project.repository.PatientInsuranceRepository;
import com.practiceProject.Ims_Project.repository.PatientRepository;
import com.practiceProject.Ims_Project.service.PatientInsuranceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientInsuranceServiceImpl implements PatientInsuranceService {

    private final PatientInsuranceRepository patientInsuranceRepository;
    private final PatientRepository patientRepository;
}
