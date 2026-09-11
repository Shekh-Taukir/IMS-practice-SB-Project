package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.controller;

import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.response.VnLabOrderResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service.VnLabOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: VisitNote LabOrder Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 08, 2026 || TaukirHp (ER 1020 - vn lab order entity coding)
// v1.2 || type : Change || Sep 11, 2026 || TaukirS (ER 1020 - vn lab order entity coding)

/// //////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/visit/{pn_Id}/vn-labOrder")
public class VnLabOrderController {

    private final VnLabOrderService vnLabOrderService;
    private static final String ID_URL = "/{id}";

    @PostMapping("")
    public ResponseEntity<VnLabOrderResponse> createVnLabOrder(@PathVariable("pn_Id") Long pnId, @Valid @RequestBody VnLabOrderRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vnLabOrderService.createVnLabOrder(pnId, request));
    }

    @GetMapping(ID_URL)
    public ResponseEntity<VnLabOrderResponse> getVnLabOrderById(@PathVariable("pn_Id") Long pnId, @PathVariable("id") Long vnLabOrderId) {
        return ResponseEntity.ok(vnLabOrderService.getVnLabOrderById(pnId, vnLabOrderId));
    }

    @GetMapping("")
    public ResponseEntity<List<VnLabOrderResponse>> getVnLabOrderList(@PathVariable("pn_Id") Long pnId) {
        return ResponseEntity.ok(vnLabOrderService.getVnLabOrderList(pnId));
    }

    @PutMapping(ID_URL)
    public ResponseEntity<VnLabOrderResponse> updateVnLabOrderById(@PathVariable("pn_Id") Long pnId, @PathVariable("id") Long vnLabOrderId, @Valid @RequestBody VnLabOrderRequest request) {
        return ResponseEntity.ok(vnLabOrderService.updateVnLabOrderById(pnId, vnLabOrderId, request));
    }

    @DeleteMapping(ID_URL)
    public ResponseEntity<Void> deleteVnLabOrderById(@PathVariable("pn_Id") Long pnId, @PathVariable("id") Long vnLabOrderId) {
        vnLabOrderService.deleteVnLabOrderById(pnId, vnLabOrderId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping(ID_URL)
    public ResponseEntity<VnLabOrderResponse> patchVnLabOrderById(@PathVariable("pn_Id") Long pnId, @PathVariable("id") Long vnLabOrderId, @RequestBody VnLabOrderPatchRequest request) {
        return ResponseEntity.ok(vnLabOrderService.patchVnLabOrderById(pnId, vnLabOrderId, request));
    }
}
