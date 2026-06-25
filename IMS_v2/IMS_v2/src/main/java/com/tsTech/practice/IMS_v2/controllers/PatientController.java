package com.tsTech.practice.IMS_v2.controllers;

import com.tsTech.practice.IMS_v2.dtos.PatientDTO;
import com.tsTech.practice.IMS_v2.service.PatientService;
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
////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("")
    public ResponseEntity<List<PatientDTO>> getAllPatients(){
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable(name = "id") Long tranId){
        return ResponseEntity.ok(patientService.getPatientById(tranId));
    }

    @PostMapping("/add")
    public ResponseEntity<PatientDTO> addPatient(@Valid @RequestBody PatientDTO patientDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.addPatient(patientDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatientById(@PathVariable("id") Long tranId, @Valid @RequestBody PatientDTO patientDTO){
        return ResponseEntity.ok(patientService.updatePatientById(tranId, patientDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePatientById(@PathVariable("id") Long tranId){
        return ResponseEntity.ok(patientService.deletePatientById(tranId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientDTO> patchPatientById(@PathVariable("id") Long tranId, @RequestBody Map<String, Object> patchData){
        return ResponseEntity.ok(patientService.patchPatientById(tranId, patchData));
    }
}
