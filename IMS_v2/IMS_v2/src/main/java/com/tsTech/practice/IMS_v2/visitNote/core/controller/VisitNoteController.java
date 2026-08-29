package com.tsTech.practice.IMS_v2.visitNote.core.controller;

import com.tsTech.practice.IMS_v2.visitNote.core.dto.request.VisitNoteRequest;
import com.tsTech.practice.IMS_v2.visitNote.core.dto.response.VisitNoteResponse;
import com.tsTech.practice.IMS_v2.visitNote.core.service.VisitNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/////////////////////////////////////////////
//
// Name: VisitNote Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 20, 2026 || TaukirS (ER 1015 - visitnote entity coding)
/////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class VisitNoteController {

    private final VisitNoteService visitNoteService;
    private static final String PAT_URL ="/patient/{patId}/visit";
    private static final String ID_URL ="/{id}";

    @PostMapping("/visit")
    public ResponseEntity<VisitNoteResponse> createVisit(@Valid @RequestBody VisitNoteRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(visitNoteService.createVisit(request));
    }

    @GetMapping(PAT_URL + ID_URL)
    public ResponseEntity<VisitNoteResponse> getVisitById(@PathVariable("patId") Long patientId, @PathVariable("id") Long pnId){
        return ResponseEntity.ok(visitNoteService.getVisitById(patientId, pnId));
    }

    @GetMapping(PAT_URL)
    public ResponseEntity<List<VisitNoteResponse>> getAllVisits(@PathVariable("patId") Long patientId){
        return ResponseEntity.ok(visitNoteService.getAllVisits(patientId));
    }


    @PutMapping(PAT_URL + ID_URL)
    public ResponseEntity<VisitNoteResponse> updateVisitById(@PathVariable("patId") Long patientId, @PathVariable("id") Long pnId, @Valid @RequestBody VisitNoteRequest request){
        return ResponseEntity.ok(visitNoteService.updateVisitById(patientId, pnId, request));
    }

    @DeleteMapping(PAT_URL + ID_URL)
    public ResponseEntity<Void> deleteVisitById(@PathVariable("patId") Long patientId, @PathVariable("id") Long pnId){
        visitNoteService.deleteVisitById(patientId, pnId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(PAT_URL + ID_URL)
    public ResponseEntity<VisitNoteResponse> patchUpdateVisitById(@PathVariable("patId") Long patientId, @PathVariable("id") Long pnId, @RequestBody Map<String, Object> patchUpdate){
        return ResponseEntity.ok(visitNoteService.patchUpdateVisitById(patientId, pnId, patchUpdate));
    }
}
