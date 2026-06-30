package com.tsTech.practice.IMS_v2.patient.controllers;

import com.tsTech.practice.IMS_v2.common.advices.ApiResponse;
import com.tsTech.practice.IMS_v2.patient.dtos.PatientInsuranceDTO;
import com.tsTech.practice.IMS_v2.patient.dtos.records.NextPriorityRecord;
import com.tsTech.practice.IMS_v2.patient.service.PatientInsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

////////////////////////////////////////////////
//
// Name: Patient Insurance Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 25, 2026 || TaukirS (ER 1003 - validation and generalize response and error coding)
////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/pat_ins")
public class PatientInsuranceController {

    private final PatientInsuranceService patientInsuranceService;

    @GetMapping("/list/{patId}")
    public ResponseEntity<List<PatientInsuranceDTO>> getAllInsuranceByPatient(@PathVariable("patId") Long patientId) {
        return ResponseEntity.ok(patientInsuranceService.getAllInsuranceByPatient(patientId));
    }

    @GetMapping("/nxt-priority/{patId}")
    public ResponseEntity<NextPriorityRecord> getNextPriority (@PathVariable("patId") Long pat_id) {
        return ResponseEntity.ok(patientInsuranceService.getNextPriority(pat_id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientInsuranceDTO> getPatientInsuranceById(@PathVariable("id") Long insId) {
        return ResponseEntity.ok(patientInsuranceService.getPatientInsuranceById(insId));
    }

    @PostMapping("")
    public ResponseEntity<PatientInsuranceDTO> addPatientInsuranceById(@RequestBody PatientInsuranceDTO patientInsuranceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientInsuranceService.addPatientInsuranceById(patientInsuranceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePatientInsuranceById(@PathVariable("id") Long insId) {
        return ResponseEntity.status(HttpStatus.OK).body(patientInsuranceService.deletePatientInsuranceById(insId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientInsuranceDTO> putPatientInsuranceById(@PathVariable("id") Long insId, @RequestBody PatientInsuranceDTO insuranceDTO) {
        return ResponseEntity.ok(patientInsuranceService.putPatientInsuranceById(insId, insuranceDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientInsuranceDTO> patchPatientInsuranceById(@PathVariable("id") Long insId, @RequestBody Map<String, Object> patchUpdates){
        return ResponseEntity.ok(patientInsuranceService.patchPatientInsuranceById(insId, patchUpdates));
    }
}
