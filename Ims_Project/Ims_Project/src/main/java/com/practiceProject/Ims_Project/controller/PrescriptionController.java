package com.practiceProject.Ims_Project.controller;

import com.practiceProject.Ims_Project.dto.PrescriptionDto;
import com.practiceProject.Ims_Project.dto.PrescriptionRequestDto;
import com.practiceProject.Ims_Project.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/presc")
@Slf4j
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetMapping("/list/{id}")
    public ResponseEntity<PrescriptionRequestDto> getPatientPrescriptions(@PathVariable("id") Long patientId){
        log.info("[controller] Fetching patient prescription for patientId : {}", patientId);
        return ResponseEntity.ok(prescriptionService.getPatientPrescriptions(patientId));
    }

    @PostMapping("")
    public ResponseEntity<PrescriptionRequestDto> createPrescription(@Valid @RequestBody PrescriptionRequestDto prescriptionRequestDto){
        log.info("[controller] Adding new prescription");

        return ResponseEntity.status(HttpStatus.CREATED).body(prescriptionService.createPrescription(prescriptionRequestDto));
    }
}
