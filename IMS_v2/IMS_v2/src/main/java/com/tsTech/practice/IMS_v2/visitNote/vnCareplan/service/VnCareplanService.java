package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.service;

import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.response.VnCareplanResponse;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: VisitNote Careplan Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1018 - visit careplan entity coding)

/// //////////////////////////////////////////

public interface VnCareplanService {
    VnCareplanResponse createVnCareplan(Long pnId, VnCareplanRequest request);

    VnCareplanResponse getVnCareplanById(Long pnId, Long vnCareplanId);

    List<VnCareplanResponse> getVnCareplanList(Long pnId);

    VnCareplanResponse updateVnCareplanById(Long pnId, Long vnCareplanId, VnCareplanRequest request);

    void deleteVnCareplanById(Long pnId, Long vnCareplanId);

    VnCareplanResponse patchVnCareplanById(Long pnId, Long vnCareplanId, VnCareplanPatchRequest request);
}
