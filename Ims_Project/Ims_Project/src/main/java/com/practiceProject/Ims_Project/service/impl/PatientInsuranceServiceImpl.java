package com.practiceProject.Ims_Project.service.impl;

import com.practiceProject.Ims_Project.dto.PatientInsuranceDto;
import com.practiceProject.Ims_Project.repository.PatientInsuranceRepository;
import com.practiceProject.Ims_Project.service.PatientInsuranceService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientInsuranceServiceImpl implements PatientInsuranceService {

    private final PatientInsuranceRepository patientInsuranceRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PatientInsuranceDto> getPatientInsuranceList() {
        return patientInsuranceRepository
                .findAll()
                .stream()
                .map((patientInsurance) -> modelMapper
                        .map(patientInsurance, PatientInsuranceDto.class))
                .toList();
    }
}
