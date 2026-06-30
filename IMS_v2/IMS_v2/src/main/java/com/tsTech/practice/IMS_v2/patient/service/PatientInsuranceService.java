package com.tsTech.practice.IMS_v2.patient.service;

import com.tsTech.practice.IMS_v2.patient.dtos.PatientInsuranceDTO;
import com.tsTech.practice.IMS_v2.patient.dtos.records.NextPriorityRecord;

import java.util.List;
import java.util.Map;

public interface PatientInsuranceService {

    List<PatientInsuranceDTO> getAllInsuranceByPatient(Long patientId);

    PatientInsuranceDTO getPatientInsuranceById(Long insId);

    PatientInsuranceDTO addPatientInsuranceById(PatientInsuranceDTO patientInsuranceDTO);

    Boolean deletePatientInsuranceById(Long insId);

    PatientInsuranceDTO putPatientInsuranceById(Long insId, PatientInsuranceDTO patientInsuranceDTO);

    PatientInsuranceDTO patchPatientInsuranceById(Long insId, Map<String, Object> patchUpdates);

    NextPriorityRecord getNextPriority(Long patId);
}
