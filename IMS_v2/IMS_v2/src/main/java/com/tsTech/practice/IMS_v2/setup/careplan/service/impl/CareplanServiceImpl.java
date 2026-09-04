package com.tsTech.practice.IMS_v2.setup.careplan.service.impl;

import com.tsTech.practice.IMS_v2.setup.careplan.dto.request.CareplanPatchRequest;
import com.tsTech.practice.IMS_v2.setup.careplan.dto.request.CareplanRequest;
import com.tsTech.practice.IMS_v2.setup.careplan.dto.response.CareplanResponse;
import com.tsTech.practice.IMS_v2.setup.careplan.entity.Careplan;
import com.tsTech.practice.IMS_v2.setup.careplan.mapper.CareplanMapper;
import com.tsTech.practice.IMS_v2.setup.careplan.repository.CareplanRepository;
import com.tsTech.practice.IMS_v2.setup.careplan.service.CareplanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Careplan Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1017 - careplan mst entity coding)

/// //////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareplanServiceImpl implements CareplanService {

    private final CareplanRepository careplanRepository;
    private final CareplanMapper careplanMapper;

    @Override
    @Transactional
    public CareplanResponse createCareplan(CareplanRequest request) {
        log.debug("Entering createCarePlan()");
        Careplan careplan = careplanMapper.fromRequestToEntity(request);

        CareplanResponse response = careplanMapper.toResponse(careplanRepository.save(careplan));

        logResult("New Careplan", "createCareplan", null, request, response);
        return response;
    }

    @Override
    public CareplanResponse getCareplanById(Long careplanId) {
        log.debug("Entering getCareplanById | careplanId: {}", careplanId);
        CareplanResponse response = careplanMapper.toResponse(
                careplanRepository.getEntityById(careplanId)
        );

        logResult("Careplan By Id", "getCareplanById", careplanId, null, response);
        return response;
    }

    @Override
    public List<CareplanResponse> getCareplanList() {
        log.debug("Entering getCareplanList");

        List<CareplanResponse> response = careplanMapper.toResponseList(
                careplanRepository.findAllByOrderByTranIdDesc()
        );

        logResult("Careplan List retrieved", "getCareplanList", null, null, response);
        return response;
    }

    @Override
    @Transactional
    public CareplanResponse updateCareplanById(Long careplanId, CareplanRequest request) {
        log.debug("Entering updateCareplanById | careplanId : {}", careplanId);
        Careplan careplan = careplanRepository.getEntityById(careplanId);

        careplanMapper.updateEntityFromRequest(request, careplan);

        CareplanResponse response = careplanMapper.toResponse(
                careplanRepository.save(careplan)
        );
        logResult("Careplan updated", "updateCareplanById", careplanId, request, response);
        return response;
    }

    @Override
    @Transactional
    public void deleteCareplanById(Long careplanId) {
        log.debug("Entering deleteCareplanById | careplanId : {}", careplanId);
        Careplan careplan = careplanRepository.getEntityById(careplanId);
        careplanRepository.delete(careplan);
        logResult("Careplan deleted", "deleteCareplanById", careplanId, null, null);
    }

    @Override
    @Transactional
    public CareplanResponse patchCareplanById(Long careplanId, CareplanPatchRequest request) {
        log.debug("Entering patchCareplanById | careplanId : {}", careplanId);

        Careplan careplan = careplanRepository.getEntityById(careplanId);
        careplanMapper.patch(request, careplan);
        CareplanResponse response = careplanMapper.toResponse(
                careplanRepository.save(careplan)
        );

        logResult("Careplan Partially updated", "patchCareplanById", careplanId, request, null);
        return response;
    }


    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    void logResult(String action, String methodName, Long careplanId, Object input, Object result) {
        String logString = "Careplan Mst: " + action + " | " + methodName;

        if (careplanId != null)
            logString += " | carePlanId: " + careplanId;

        log.debug(logString);

        if (log.isTraceEnabled()) {
            if (input != null)
                logString += " | Input Data: " + input;

            if (result != null)
                logString += " | Result Data: " + result;

            log.trace(logString);
        }

    }
}
