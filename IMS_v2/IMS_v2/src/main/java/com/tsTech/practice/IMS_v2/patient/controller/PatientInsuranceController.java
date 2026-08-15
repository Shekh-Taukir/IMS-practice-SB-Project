package com.tsTech.practice.IMS_v2.patient.controller;

import com.tsTech.practice.IMS_v2.patient.dto.records.NextPriorityRecord;
import com.tsTech.practice.IMS_v2.patient.dto.records.request.PatientInsuranceRequest;
import com.tsTech.practice.IMS_v2.patient.dto.records.response.PatientInsuranceResponse;
import com.tsTech.practice.IMS_v2.patient.service.PatientInsuranceService;
import jakarta.validation.Valid;
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
// v1.2 || type : Change || Jul 24, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
//Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes) - added /v1 and /{patId} as patient insurance is depended on patient entirely
@RequestMapping("/v1/{patId}/pat_ins")
public class PatientInsuranceController {

    private final PatientInsuranceService patientInsuranceService;
    //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    private final String idUrl = "/{id}";

    @GetMapping("/list")
    public ResponseEntity<List<PatientInsuranceResponse>> getAllInsuranceByPatient(@PathVariable("patId") Long patientId) {
        return ResponseEntity.ok(patientInsuranceService.getAllInsuranceByPatient(patientId));
    }

    @GetMapping("/nxt-priority")
    public ResponseEntity<NextPriorityRecord> getNextPriority (@PathVariable("patId") Long patientId) {
        return ResponseEntity.ok(patientInsuranceService.getNextPriority(patientId));
    }

    @GetMapping(idUrl)
    public ResponseEntity<PatientInsuranceResponse> getPatientInsuranceById(@PathVariable("patId") Long patientId, @PathVariable("id") Long insId) {
        return ResponseEntity.ok(patientInsuranceService.getPatientInsuranceById(patientId, insId));
    }

    @PostMapping("")
    public ResponseEntity<PatientInsuranceResponse> addPatientInsuranceById(@PathVariable("patId") Long patientId,@Valid @RequestBody PatientInsuranceRequest patientInsuranceRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientInsuranceService.addPatientInsuranceById(patientId, patientInsuranceRequest));
    }

    @DeleteMapping(idUrl)
    public ResponseEntity<Boolean> deletePatientInsuranceById(@PathVariable("patId") Long patientId, @PathVariable("id") Long insId) {
        return ResponseEntity.status(HttpStatus.OK).body(patientInsuranceService.deletePatientInsuranceById(patientId, insId));
    }

    @PutMapping(idUrl)
    public ResponseEntity<PatientInsuranceResponse> putPatientInsuranceById(@PathVariable("patId") Long patientId, @PathVariable("id") Long insId, @Valid @RequestBody PatientInsuranceRequest patientInsuranceRequest) {
        return ResponseEntity.ok(patientInsuranceService.putPatientInsuranceById(patientId, insId, patientInsuranceRequest));
    }

    @PatchMapping(idUrl)
    public ResponseEntity<PatientInsuranceResponse> patchPatientInsuranceById(@PathVariable("patId") Long patientId, @PathVariable("id") Long insId, @RequestBody Map<String, Object> patchUpdates){
        return ResponseEntity.ok(patientInsuranceService.patchPatientInsuranceById(patientId, insId, patchUpdates));
    }
}
