package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service;

import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.response.VnLabOrderResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface VnLabOrderService {
    VnLabOrderResponse createVnLabOrder(Long pnId, VnLabOrderRequest request);

    VnLabOrderResponse getVnLabOrderById(Long pnId, Long vnLabOrderId);

    List<VnLabOrderResponse> getVnLabOrderList(Long pnId);

    VnLabOrderResponse updateVnLabOrderById(Long pnId, Long vnLabOrderId, VnLabOrderRequest request);

    void deleteVnLabOrderById(Long pnId, Long vnLabOrderId);

    VnLabOrderResponse patchVnLabOrderById(Long pnId, Long vnLabOrderId, VnLabOrderPatchRequest request);
}
