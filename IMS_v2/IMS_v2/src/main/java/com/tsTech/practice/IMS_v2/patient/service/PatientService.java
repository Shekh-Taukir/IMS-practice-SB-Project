package com.tsTech.practice.IMS_v2.patient.service;

import com.tsTech.practice.IMS_v2.patient.dtos.PatientDTO;

import java.util.List;
import java.util.Map;

public interface PatientService {
    List<PatientDTO> getAllPatients();

    PatientDTO addPatient(PatientDTO patientDTO);

    PatientDTO getPatientById(Long tranId);

    PatientDTO updatePatientById(Long tranId, PatientDTO patientDTO);

    Boolean deletePatientById(Long tranId);

    PatientDTO patchPatientById(Long tranId, Map<String, Object> patchData);
}
