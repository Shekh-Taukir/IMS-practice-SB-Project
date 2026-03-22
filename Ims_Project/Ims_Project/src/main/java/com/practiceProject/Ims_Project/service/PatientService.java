package com.practiceProject.Ims_Project.service;

import com.practiceProject.Ims_Project.advices.PageResponse;
import com.practiceProject.Ims_Project.dto.PatientBloodGroupCountDto;
import com.practiceProject.Ims_Project.dto.PatientDto;
import com.practiceProject.Ims_Project.entity.Patient;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PatientService {

    List<PatientDto> getPatientsList();

    PatientDto createPatient(PatientDto patientDto);

    PatientDto getPatientById(Long patientId);

    PatientDto updatePatient(Long patientId, PatientDto patientDto);

    String deletePatientById(Long patientId);

    PatientDto updatePartialPatient(Long patientId, Map<String, Object> updates);

    List<PatientDto> getPatientsListPaged(String sortBy, String dir, Integer page);

    Page<Patient> getPatientsFullPagedList(String sortBy, String dir, Integer page);

    PageResponse<PatientDto> getCustomPatientPage(String sortBy, String dir, Integer page);

    PageResponse<PatientDto> findPatientByFirstName(String sortBy, String dir, Integer page, String lastName);

    List<PatientBloodGroupCountDto> getBloodGroupStatsList();

    PatientDto updatePatientNameById(Long id, String lastName);
}
