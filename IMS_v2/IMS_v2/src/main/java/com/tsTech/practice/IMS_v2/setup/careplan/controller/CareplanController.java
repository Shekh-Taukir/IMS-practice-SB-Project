package com.tsTech.practice.IMS_v2.setup.careplan.controller;

import com.tsTech.practice.IMS_v2.setup.careplan.dto.request.CareplanPatchRequest;
import com.tsTech.practice.IMS_v2.setup.careplan.dto.request.CareplanRequest;
import com.tsTech.practice.IMS_v2.setup.careplan.dto.response.CareplanResponse;
import com.tsTech.practice.IMS_v2.setup.careplan.service.CareplanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Careplan Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1017 - careplan mst entity coding)

/// //////////////////////////////////////////

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/careplan")
public class CareplanController {

    private final CareplanService careplanService;
    private static final String ID_URL = "/{id}";

    @PostMapping("")
    public ResponseEntity<CareplanResponse> createCareplan(@Valid @RequestBody CareplanRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(careplanService.createCareplan(request));
    }

    @GetMapping(ID_URL)
    public ResponseEntity<CareplanResponse> getCareplanById(@PathVariable("id") Long careplanId) {
        return ResponseEntity.ok(careplanService.getCareplanById(careplanId));
    }

    @GetMapping("")
    public ResponseEntity<List<CareplanResponse>> getCareplanList() {
        return ResponseEntity.ok(careplanService.getCareplanList());
    }

    @PutMapping(ID_URL)
    public ResponseEntity<CareplanResponse> updateCareplanById(@PathVariable("id") Long careplanId, @Valid @RequestBody CareplanRequest request) {
        return ResponseEntity.ok(careplanService.updateCareplanById(careplanId, request));
    }

    @DeleteMapping(ID_URL)
    public ResponseEntity<Void> deleteCareplanById(@PathVariable("id") Long careplanId) {
        careplanService.deleteCareplanById(careplanId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping(ID_URL)
    public ResponseEntity<CareplanResponse> patchCareplanById(@PathVariable("id") Long careplanId, @RequestBody CareplanPatchRequest request) {
        return ResponseEntity.ok(careplanService.patchCareplanById(careplanId, request));
    }


}
