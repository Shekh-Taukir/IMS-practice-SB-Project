package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.controller;

import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderIcdMapRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.response.VnLabOrderIcdMapResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service.VnLabOrderIcdMapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Vn Lab Order Icd Map Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 11, 2026 || TaukirS (ER 1022 - vn lab order icd map entity coding)

/// //////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vn-labOrder/{vnLabOrderId}/icd-map")
public class VnLabOrderIcdMapController {

    private final VnLabOrderIcdMapService service;

    @PostMapping("")
    public ResponseEntity<List<VnLabOrderIcdMapResponse>> createVnLabOrderIcdMap(@PathVariable Long vnLabOrderId, @Valid @RequestBody VnLabOrderIcdMapRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createVnLabOrderIcdMap(vnLabOrderId, request));
    }

    @GetMapping("")
    public ResponseEntity<List<VnLabOrderIcdMapResponse>> getVnLabOrderIcdMapList(@PathVariable Long vnLabOrderId) {
        return ResponseEntity.ok(service.getVnLabOrderIcdMapList(vnLabOrderId));
    }

    @PutMapping("")
    public ResponseEntity<List<VnLabOrderIcdMapResponse>> updateVnLabOrderIcdMapById(@PathVariable Long vnLabOrderId, @Valid @RequestBody VnLabOrderIcdMapRequest request) {
        return ResponseEntity.ok(service.updateVnLabOrderIcdMapById(vnLabOrderId, request));
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteVnLabOrderIcdMap(@PathVariable Long vnLabOrderId) {
        service.deleteVnLabOrderIcdMap(vnLabOrderId);
        return ResponseEntity.noContent().build();
    }
}
