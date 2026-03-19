package com.practiceProject.Ims_Project.service;

import com.practiceProject.Ims_Project.dto.PatientDto;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface PatientService {

    List<PatientDto> getPatientsList();

    PatientDto createPatient(PatientDto patientDto);

    PatientDto getPatientById(Long patientId);

    PatientDto updatePatient(Long patientId, PatientDto patientDto);

    String deletePatientById(Long patientId);

    PatientDto updatePartialPatient(Long patientId, Map<String, Object> updates);
}
