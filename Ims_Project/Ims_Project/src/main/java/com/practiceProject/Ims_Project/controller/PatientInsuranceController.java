package com.practiceProject.Ims_Project.controller;

import com.practiceProject.Ims_Project.dto.PatientInsuranceDto;
import com.practiceProject.Ims_Project.service.PatientInsuranceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Patient Insurance", description = "Patient Insurance Api's")
@RequestMapping("/pat_ins")
@RequiredArgsConstructor
@RestController
public class PatientInsuranceController {

    private final PatientInsuranceService patientInsuranceService;

    @GetMapping("/list")
    public ResponseEntity<List<PatientInsuranceDto>> getPatientInsuranceList(){
        return ResponseEntity.ok(patientInsuranceService.getPatientInsuranceList());
    }
}
