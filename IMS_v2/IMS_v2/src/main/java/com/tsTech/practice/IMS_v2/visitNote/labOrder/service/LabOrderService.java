package com.tsTech.practice.IMS_v2.visitNote.labOrder.service;

import com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.request.LabOrderPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.request.LabOrderRequest;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.response.LabOrderResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface LabOrderService {
    LabOrderResponse createLabOrder(LabOrderRequest request);

    LabOrderResponse getLabOrderById(Long labOrderId);

    List<LabOrderResponse> getLabOrderList();

    LabOrderResponse updateLabOrderById(Long labOrderId, LabOrderRequest request);

    void deleteLabOrderById(Long labOrderId);

    LabOrderResponse patchLabOrderById(Long labOrderId, LabOrderPatchRequest request);
}
