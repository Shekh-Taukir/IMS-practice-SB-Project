package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.controller;

import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanIcdMapRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.response.VnCareplanIcdMapResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.service.VnCareplanIcdMapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Vn Careplan ICD Map Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 08, 2026 || TaukirS (ER 1021 - vn careplan icd map entity coding)

/// //////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vn-careplan/{vnCareplanId}/icd-map")
public class VnCareplanIcdMapController {

    private final VnCareplanIcdMapService service;

    @PostMapping("")
    public ResponseEntity<List<VnCareplanIcdMapResponse>> createVnCareplanIcdMap(@PathVariable Long vnCareplanId, @Valid @RequestBody VnCareplanIcdMapRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createVnCareplanIcdMap(vnCareplanId, request));
    }

    @GetMapping("")
    public ResponseEntity<List<VnCareplanIcdMapResponse>> getVnCareplanIcdMapList(@PathVariable Long vnCareplanId) {
        return ResponseEntity.ok(service.getVnCareplanIcdMapList(vnCareplanId));
    }

    @PutMapping("")
    public ResponseEntity<List<VnCareplanIcdMapResponse>> updateVnCareplanIcdMapById(@PathVariable Long vnCareplanId, @Valid @RequestBody VnCareplanIcdMapRequest request) {
        return ResponseEntity.ok(service.updateVnCareplanIcdMapById(vnCareplanId, request));
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteVnCareplanIcdMap(@PathVariable Long vnCareplanId) {
        service.deleteVnCareplanIcdMap(vnCareplanId);
        return ResponseEntity.noContent().build();
    }
}
