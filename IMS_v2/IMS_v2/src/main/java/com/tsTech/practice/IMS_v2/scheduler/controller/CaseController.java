package com.tsTech.practice.IMS_v2.scheduler.controller;

import com.tsTech.practice.IMS_v2.scheduler.dto.request.CaseRequest;
import com.tsTech.practice.IMS_v2.scheduler.dto.response.CaseResponse;
import com.tsTech.practice.IMS_v2.scheduler.service.CaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/////////////////////////////////////////////
//
// Name: Case Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 16, 2026 || TaukirS (ER 1013 - case master coding)
/////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/{pat_id}/case")
public class CaseController {

    private final CaseService caseService;
    private static final String ID_URL = "/{id}";

    @PostMapping("")
    public ResponseEntity<CaseResponse> createCase(@PathVariable("pat_id") Long patientId, @Valid @RequestBody CaseRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(caseService.createCase(patientId, request));
    }

    @GetMapping(ID_URL)
    public ResponseEntity<CaseResponse> getCaseById(@PathVariable("pat_id") Long patientId, @PathVariable("id") Long caseId){
        return ResponseEntity.ok(caseService.getCaseById(patientId, caseId));
    }

    @GetMapping("")
    public ResponseEntity<List<CaseResponse>> getAllCases(@PathVariable("pat_id") Long patientId){
        return ResponseEntity.ok(caseService.getAllCases(patientId));
    }

    @PutMapping(ID_URL)
    public ResponseEntity<CaseResponse> updateCaseById(@PathVariable("pat_id") Long patientId, @PathVariable("id") Long caseId, @Valid @RequestBody CaseRequest request){
        return ResponseEntity.ok(caseService.updateCaseById(patientId, caseId, request));
    }

    @DeleteMapping(ID_URL)
    public ResponseEntity<Void> deleteCaseById(@PathVariable("pat_id") Long patientId, @PathVariable("id") Long caseId){
        caseService.deleteCaseById(patientId, caseId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(ID_URL)
    public ResponseEntity<CaseResponse> patchCaseById(@PathVariable("pat_id") Long patientId, @PathVariable("id") Long caseId, @RequestBody Map<String, Object> patchData){
        return ResponseEntity.ok(caseService.patchCaseById(patientId, caseId, patchData));
    }
}
