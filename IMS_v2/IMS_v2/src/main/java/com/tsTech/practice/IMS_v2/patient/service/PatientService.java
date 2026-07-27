package com.tsTech.practice.IMS_v2.patient.service;

import com.tsTech.practice.IMS_v2.patient.dtos.records.request.PatientRequest;
import com.tsTech.practice.IMS_v2.patient.dtos.records.response.PatientResponse;

import java.util.List;
import java.util.Map;

/////////////////////////////////////////////
//
// Name: Patient Master Service Interface
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 24, 2026 || TaukirS (ER 1002 - patient mst Api's)
// v1.2 || type : Change || Jul 24, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

public interface PatientService {
    List<PatientResponse> getAllPatients();

    PatientResponse getPatientById(Long patientId);

    PatientResponse addPatient(PatientRequest patientRequest);

    PatientResponse updatePatientById(Long patientId, PatientRequest patientRequest);

    Boolean deletePatientById(Long patientId);

    PatientResponse patchPatientById(Long patientId, Map<String, Object> patchData);
}
