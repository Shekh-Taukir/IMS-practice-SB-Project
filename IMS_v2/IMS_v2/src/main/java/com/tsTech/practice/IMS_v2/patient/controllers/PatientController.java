package com.tsTech.practice.IMS_v2.patient.controllers;

import com.tsTech.practice.IMS_v2.patient.dtos.records.PatientRequest;
import com.tsTech.practice.IMS_v2.patient.dtos.records.PatientResponse;
import com.tsTech.practice.IMS_v2.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

////////////////////////////////////////////////
//
// Name: PatientController
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
// v1.2 || type : Change || Jun 18, 2026 || TaukirS (ER 1002 - patient mst apis)
// v1.3 || type : Change || Jun 25, 2026 || TaukirS (ER 1003 - validation and generalize response and error coding)
// v1.4 || type : Change || Jul 23, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/patient") //Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes) - added v1/ in the patient base url
public class PatientController {

    private final PatientService patientService;
    private final String idUrl = "/{id}";      //Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes)

    @GetMapping("")
    public ResponseEntity<List<PatientResponse>> getAllPatients(){
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping(idUrl)
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable(name = "id") Long patientId){
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

    @PostMapping("")
    public ResponseEntity<PatientResponse> addPatient(@Valid @RequestBody PatientRequest patientRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.addPatient(patientRequest));
    }

    @PutMapping(idUrl)
    public ResponseEntity<PatientResponse> updatePatientById(@PathVariable("id") Long patientId, @Valid @RequestBody PatientRequest patientRequest){
        return ResponseEntity.ok(patientService.updatePatientById(patientId, patientRequest));
    }

    @DeleteMapping(idUrl)
    public ResponseEntity<Boolean> deletePatientById(@PathVariable("id") Long patientId){
        return ResponseEntity.ok(patientService.deletePatientById(patientId));
    }

    @PatchMapping(idUrl)
    public ResponseEntity<PatientResponse> patchPatientById(@PathVariable("id") Long patientId, @RequestBody Map<String, Object> patchData){
        return ResponseEntity.ok(patientService.patchPatientById(patientId, patchData));
    }
}
