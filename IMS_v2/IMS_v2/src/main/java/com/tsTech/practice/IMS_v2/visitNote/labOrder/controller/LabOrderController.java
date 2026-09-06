package com.tsTech.practice.IMS_v2.visitNote.labOrder.controller;

import com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.request.LabOrderPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.request.LabOrderRequest;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.response.LabOrderResponse;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.service.LabOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: LabOrder Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 06, 2026 || TaukirS (ER 1019 - lab order entity coding)
/// //////////////////////////////////////////

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/lab-order")
public class LabOrderController {

    private final LabOrderService labOrderService;
    private static final String ID_URL = "/{id}";

    @PostMapping("")
    public ResponseEntity<LabOrderResponse> createLabOrder(@Valid @RequestBody LabOrderRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(labOrderService.createLabOrder(request));
    }

    @GetMapping(ID_URL)
    public ResponseEntity<LabOrderResponse> getLabOrderById(@PathVariable("id") Long labOrderId) {
        return ResponseEntity.ok(labOrderService.getLabOrderById(labOrderId));
    }

    @GetMapping("")
    public ResponseEntity<List<LabOrderResponse>> getLabOrderList() {
        return ResponseEntity.ok(labOrderService.getLabOrderList());
    }

    @PutMapping(ID_URL)
    public ResponseEntity<LabOrderResponse> updateLabOrderById(@PathVariable("id") Long labOrderId, @Valid @RequestBody LabOrderRequest request) {
        return ResponseEntity.ok(labOrderService.updateLabOrderById(labOrderId, request));
    }

    @DeleteMapping(ID_URL)
    public ResponseEntity<Void> deleteLabOrderById(@PathVariable("id") Long labOrderId) {
        labOrderService.deleteLabOrderById(labOrderId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping(ID_URL)
    public ResponseEntity<LabOrderResponse> patchLabOrderById(@PathVariable("id") Long labOrderId, @RequestBody LabOrderPatchRequest request) {
        return ResponseEntity.ok(labOrderService.patchLabOrderById(labOrderId, request));
    }

}
