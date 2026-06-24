package com.tsTech.practice.IMS_v2.service;

import com.tsTech.practice.IMS_v2.dtos.PatientDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PatientService {
    List<PatientDTO> getAllPatients();

    PatientDTO addPatient(PatientDTO patientDTO);

    Optional<PatientDTO> getPatientById(Long tranId);

    Optional<PatientDTO> updatePatientById(Long tranId, PatientDTO patientDTO);

    Boolean deletePatientById(Long tranId);

    Optional<PatientDTO> patchPatientById(Long tranId, Map<String, Object> patchData);
}
