package com.tsTech.practice.IMS_v2.visitNote.diagnosis.controller;

import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisCreateRequest;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.response.DiagnosisResponse;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.service.DiagnosisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/////////////////////////////////////////////
//
// Name: Diagnosis Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
/////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class DiagnosisController {

    private final DiagnosisService diagnosisService;
    private static final String VISIT_URL = "/visit/{pnId}/diagnosis";
    private static final String ID_URL = "/{id}";

    @PostMapping(VISIT_URL)
    public ResponseEntity<DiagnosisResponse> createDiagnosis(@PathVariable("pnId") Long pnId, @Valid @RequestBody DiagnosisCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(diagnosisService.createDiagnosis(pnId, request));
    }

    @GetMapping(VISIT_URL+ID_URL)
    public ResponseEntity<DiagnosisResponse> getDiagnosisById(@PathVariable("pnId") Long pnId, @PathVariable("id") Long diagnosisId){
        return ResponseEntity.ok(diagnosisService.getDiagnosisById(pnId, diagnosisId));
    }

    @GetMapping("/diagnosis/patient/{patId}")
    public ResponseEntity<List<DiagnosisResponse>> getDiagnosisByPatientId(@PathVariable("patId") Long patientId){
        return ResponseEntity.ok(diagnosisService.getDiagnosisByPatientId(patientId));
    }

    @PutMapping(VISIT_URL+ID_URL)
    public ResponseEntity<DiagnosisResponse> updateDiagnosis(@PathVariable("pnId") Long pnId, @PathVariable("id") Long diagnosisId, @Valid @RequestBody DiagnosisCreateRequest request){
        return ResponseEntity.ok(diagnosisService.updateDiagnosis(pnId, diagnosisId, request));
    }

    @PatchMapping(VISIT_URL+ID_URL)
    public ResponseEntity<DiagnosisResponse> patchUpdateDiangnosisById(@PathVariable("pnId") Long pnId, @PathVariable("id") Long diagnosisId, @RequestBody DiagnosisPatchRequest request){
        return ResponseEntity.ok(diagnosisService.patchUpdateDiangnosisById(pnId, diagnosisId, request));
    }

    @DeleteMapping(VISIT_URL+ID_URL)
    public ResponseEntity<Void> deleteDiagnosisById(@PathVariable("pnId") Long pnId, @PathVariable("id") Long diagnosisId){
        diagnosisService.deleteDiagnosisById(pnId, diagnosisId);
        return ResponseEntity.noContent().build();
    }
}
