package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service;

import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderIcdMapRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.response.VnLabOrderIcdMapResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface VnLabOrderIcdMapService {
    List<VnLabOrderIcdMapResponse> createVnLabOrderIcdMap(Long vnLabOrderId, VnLabOrderIcdMapRequest request);

    List<VnLabOrderIcdMapResponse> getVnLabOrderIcdMapList(Long vnLabOrderId);

    List<VnLabOrderIcdMapResponse> updateVnLabOrderIcdMapById(Long vnLabOrderId, VnLabOrderIcdMapRequest request);

    void deleteVnLabOrderIcdMap(Long vnLabOrderId);
}
