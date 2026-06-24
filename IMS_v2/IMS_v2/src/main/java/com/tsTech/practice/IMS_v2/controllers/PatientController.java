package com.tsTech.practice.IMS_v2.controllers;

import com.tsTech.practice.IMS_v2.dtos.PatientDTO;
import com.tsTech.practice.IMS_v2.service.PatientService;
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

        return patientService
                .getPatientById(tranId)
                .map(patientDTO1->{return ResponseEntity.ok(patientDTO1);})
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<PatientDTO> addPatient(@RequestBody PatientDTO patientDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.addPatient(patientDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatientById(@PathVariable("id") Long tranId, @RequestBody PatientDTO patientDTO){

        return patientService
                .updatePatientById(tranId, patientDTO)
                .map(patientDTO1 -> { return ResponseEntity.ok(patientDTO1); })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePatientById(@PathVariable("id") Long tranId){
        if(patientService.deletePatientById(tranId))
            return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientDTO> patchPatientById(@PathVariable("id") Long tranId, @RequestBody Map<String, Object> patchData){
        return patientService
                .patchPatientById(tranId, patchData)
                .map(patientDTO -> { return ResponseEntity.ok(patientDTO);})
                .orElse(ResponseEntity.notFound().build());
    }
}
