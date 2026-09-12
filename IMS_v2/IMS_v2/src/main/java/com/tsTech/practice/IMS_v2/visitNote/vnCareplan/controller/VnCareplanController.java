package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.controller;

import com.tsTech.practice.IMS_v2.setup.careplan.dto.request.CareplanPatchRequest;
import com.tsTech.practice.IMS_v2.setup.careplan.dto.request.CareplanRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.response.VnCareplanResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.service.VnCareplanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: VisitNote Careplan Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1018 - visit careplan entity coding)

/// //////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/visit/{pn_Id}/vn-careplan")
public class VnCareplanController {  

    private final VnCareplanService vnCareplanService;
    private static final String ID_URL = "/{id}";

    @PostMapping("")
    public ResponseEntity<VnCareplanResponse> createVnCareplan(@PathVariable("pn_Id") Long pnId, @Valid @RequestBody VnCareplanRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vnCareplanService.createVnCareplan(pnId, request));
    }

    @GetMapping(ID_URL)
    public ResponseEntity<VnCareplanResponse> getVnCareplanById(@PathVariable("pn_Id") Long pnId, @PathVariable("id") Long vnCareplanId) {
        return ResponseEntity.ok(vnCareplanService.getVnCareplanById(pnId, vnCareplanId));
    }

    @GetMapping("")
    public ResponseEntity<List<VnCareplanResponse>> getVnCareplanList(@PathVariable("pn_Id") Long pnId) {
        return ResponseEntity.ok(vnCareplanService.getVnCareplanList(pnId));
    }

    @PutMapping(ID_URL)
    public ResponseEntity<VnCareplanResponse> updateVnCareplanById(@PathVariable("pn_Id") Long pnId, @PathVariable("id") Long vnCareplanId, @Valid @RequestBody VnCareplanRequest request) {
        return ResponseEntity.ok(vnCareplanService.updateVnCareplanById(pnId, vnCareplanId, request));
    }

    @DeleteMapping(ID_URL)
    public ResponseEntity<Void> deleteVnCareplanById(@PathVariable("pn_Id") Long pnId, @PathVariable("id") Long vnCareplanId) {
        vnCareplanService.deleteVnCareplanById(pnId, vnCareplanId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping(ID_URL)
    public ResponseEntity<VnCareplanResponse> patchVnCareplanById(@PathVariable("pn_Id") Long pnId, @PathVariable("id") Long vnCareplanId, @RequestBody VnCareplanPatchRequest request) {
        return ResponseEntity.ok(vnCareplanService.patchVnCareplanById(pnId, vnCareplanId, request));
    }
}
