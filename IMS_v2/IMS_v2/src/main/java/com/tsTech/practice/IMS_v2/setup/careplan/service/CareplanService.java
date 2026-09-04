package com.tsTech.practice.IMS_v2.setup.careplan.service;

import com.tsTech.practice.IMS_v2.setup.careplan.dto.request.CareplanPatchRequest;
import com.tsTech.practice.IMS_v2.setup.careplan.dto.request.CareplanRequest;
import com.tsTech.practice.IMS_v2.setup.careplan.dto.response.CareplanResponse;

import java.util.List;

public interface CareplanService {
    CareplanResponse createCareplan(CareplanRequest request);

    CareplanResponse getCareplanById(Long careplanId);

    List<CareplanResponse> getCareplanList();

    CareplanResponse updateCareplanById(Long careplanId, CareplanRequest request);

    void deleteCareplanById(Long careplanId);

    CareplanResponse patchCareplanById(Long careplanId, CareplanPatchRequest request);
}
