package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.service;

import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanIcdMapRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.response.VnCareplanIcdMapResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface VnCareplanIcdMapService {

    List<VnCareplanIcdMapResponse> createVnCareplanIcdMap(Long vnCareplanId, VnCareplanIcdMapRequest request);

    List<VnCareplanIcdMapResponse> getVnCareplanIcdMapList(Long vnCareplanId);

    List<VnCareplanIcdMapResponse> updateVnCareplanIcdMapById(Long vnCareplanId, VnCareplanIcdMapRequest request);

    void deleteVnCareplanIcdMap(Long vnCareplanId);
}
