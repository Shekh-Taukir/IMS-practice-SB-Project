package com.tsTech.practice.IMS_v2.patient.service;

import com.tsTech.practice.IMS_v2.patient.dtos.records.NextPriorityRecord;
import com.tsTech.practice.IMS_v2.patient.dtos.records.request.PatientInsuranceRequest;
import com.tsTech.practice.IMS_v2.patient.dtos.records.response.PatientInsuranceResponse;

import java.util.List;
import java.util.Map;

/////////////////////////////////////////////
//
// Name: Patient Insurance Service Interface
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 01, 2026 || TaukirS (ER 1005 - patient insurance setup)
// v1.2 || type : Change || Jul 24, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

public interface PatientInsuranceService {

    List<PatientInsuranceResponse> getAllInsuranceByPatient(Long patientId);

    PatientInsuranceResponse getPatientInsuranceById(Long patientId, Long insId);

    PatientInsuranceResponse addPatientInsuranceById(Long patientId, PatientInsuranceRequest patientInsuranceDTO);

    Boolean deletePatientInsuranceById(Long patientId, Long insId);

    PatientInsuranceResponse putPatientInsuranceById(Long patientId, Long insId, PatientInsuranceRequest patientInsuranceDTO);

    PatientInsuranceResponse patchPatientInsuranceById(Long patientId, Long insId, Map<String, Object> patchUpdates);

    NextPriorityRecord getNextPriority(Long patId);
}
