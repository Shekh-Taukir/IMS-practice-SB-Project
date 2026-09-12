package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service.impl;

import com.tsTech.practice.IMS_v2.common.exception.DuplicateResourceException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.setup.icd.entities.ICD;
import com.tsTech.practice.IMS_v2.setup.icd.repository.IcdRepository;
import com.tsTech.practice.IMS_v2.setup.icd.service.IcdComUtils;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisIcdMapRequest;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.service.DiagnosisIcdMapUtils;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.projection.VnLabOrderIcdMapProjection;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderIcdMapRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.response.VnLabOrderIcdMapResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity.VnLabOrder;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity.VnLabOrderIcdMap;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.mapper.VnLabOrderIcdMapMapper;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.repository.VnLabOrderIcdMapRepository;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.repository.VnLabOrderRepository;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service.VnLabOrderIcdMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/// //////////////////////////////////////////
//
// Name: Vn Lab Order Icd Map Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 11, 2026 || TaukirS (ER 1022 - vn lab order icd map entity coding)

/// //////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VnLabOrderIcdMapServiceImpl implements VnLabOrderIcdMapService {

    private final VnLabOrderIcdMapRepository repository;
    private final VnLabOrderRepository vnLabOrderRepository;
    private final IcdRepository icdRepository;

    private final VnLabOrderIcdMapMapper mapper;
    private final DiagnosisIcdMapUtils diagnosisIcdMapUtils;

    @Override
    @Transactional
    public List<VnLabOrderIcdMapResponse> createVnLabOrderIcdMap(Long vnLabOrderId, VnLabOrderIcdMapRequest request) {
        log.debug("Entering createVnLabOrderIcdMap | vnLabOrderId: {}", vnLabOrderId);

        VnLabOrder vnLabOrder = vnLabOrderRepository.getEntityById(vnLabOrderId);
        IcdComUtils.checkDuplicateSeqAndIcdInRequest(request.icdItemList());

        if (repository.existsByVnLabOrder_TranId(vnLabOrderId))
            throw new DuplicateResourceException("DUPLICATE_VN_LABORDER_ICD_MAP", "Icd Mapping already exists for vnLabOrderId: " + vnLabOrderId);

        Map<Long, Long> requestIcdMap = DiagnosisIcdMapRequest.getRequstIcdMap(request.icdItemList());

        Map<Long, ICD> newIcdMap = IcdComUtils.getNewIcdsFromReq(requestIcdMap.keySet(), icdRepository);

        diagnosisIcdMapUtils.updateDiagnosisForNewIcds(requestIcdMap.keySet(), vnLabOrder.getVisitNote().getTranId());

        List<VnLabOrderIcdMap> newLabOrderIcdMap = newIcdMap
                .entrySet()
                .stream()
                .map(icdMap -> VnLabOrderIcdMap
                        .builder()
                        .icd(icdMap.getValue())
                        .seq(requestIcdMap.get(icdMap.getKey()))
                        .vnLabOrder(vnLabOrder)
                        .build())
                .toList();

        List<VnLabOrderIcdMapResponse> response = mapper.toResponseList(
                repository.saveAll(newLabOrderIcdMap)
        );

        logResult("VnLabOrderIcdMap Created", "createVnLabOrderIcdMap", vnLabOrderId, request, response);
        return response;
    }

    @Override
    public List<VnLabOrderIcdMapResponse> getVnLabOrderIcdMapList(Long vnLabOrderId) {
        log.debug("Entering getVnLabOrderIcdMapList | vnLabOrderId: {}", vnLabOrderId);

        List<VnLabOrderIcdMapProjection> icdMapProjections = repository.getVnLabOrderIcdProjection(vnLabOrderId);

        List<VnLabOrderIcdMapResponse> response = mapper.fromProjectionToResponse(icdMapProjections);
        logResult("VnLabOrderIcdMap List Retrieved", "getVnLabOrderIcdMapList", vnLabOrderId, null, response);
        return response;
    }

    @Override
    @Transactional
    public List<VnLabOrderIcdMapResponse> updateVnLabOrderIcdMapById(Long vnLabOrderId, VnLabOrderIcdMapRequest request) {
        log.debug("Entering updateVnLabOrderIcdMap | vnLabOrderId: {}", vnLabOrderId);

        VnLabOrder vnLabOrder = vnLabOrderRepository.getEntityById(vnLabOrderId);
        IcdComUtils.checkDuplicateSeqAndIcdInRequest(request.icdItemList());
        Map<Long, Long> requestIcdMap = DiagnosisIcdMapRequest.getRequstIcdMap(request.icdItemList());

        Map<Long, VnLabOrderIcdMap> icdMaps = repository
                .findByVnLabOrder_TranId(vnLabOrderId)
                .stream()
                .collect(Collectors.toMap(
                        x -> x.getIcd().getTranId(),
                        Function.identity()));

        // get the list of icds which needs to be deleted : (existing - request) icd list
        Set<Long> toBeDeletedIcdSet = icdMaps
                .keySet()
                .stream()
                .filter(x -> !requestIcdMap.containsKey(x))
                .collect(Collectors.toSet());

        //after getting list, remove it from icdMap, and collect a list so that it can be directly passed to deleteAll()
        List<VnLabOrderIcdMap> toBeDeletedIcdList = toBeDeletedIcdSet
                .stream()
                .map(icdMaps::remove)
                .toList();

        requestIcdMap
                .forEach((newIcdId, newSeq) -> {
                    if ((icdMaps.containsKey(newIcdId)) &&
                            !icdMaps.get(newIcdId).getSeq().equals(newSeq)) {
                        icdMaps.get(newIcdId).setSeq(newSeq);
                    }
                });

        //get list of icds which are new in request, and doesn't exists in current list
        Set<Long> newIcdSet = requestIcdMap
                .keySet()
                .stream()
                .filter(x -> !icdMaps.containsKey(x))
                .collect(Collectors.toSet());

        Map<Long, ICD> newIcdMap = IcdComUtils.getNewIcdsFromReq(newIcdSet, icdRepository);

        //this adds up new icds from request list into DiagnosisIcdMap which falls under following pnId
        diagnosisIcdMapUtils.updateDiagnosisForNewIcds(requestIcdMap.keySet(), vnLabOrder.getVisitNote().getTranId());

        //loops through the new icd's and add the VnCareplanIcd to list.
        newIcdMap.forEach((key, value) -> {
            icdMaps.put(key, VnLabOrderIcdMap
                    .builder()
                    .seq(requestIcdMap.get(key))
                    .icd(value)
                    .vnLabOrder(vnLabOrder)
                    .build()
            );
        });

        repository.deleteAll(toBeDeletedIcdList);
        List<VnLabOrderIcdMapResponse> response = mapper.toResponseList(
                repository.saveAll(icdMaps.values())
        );

        logResult("Update VnLabOrderICdMaps ", "updateVnLabOrderICdMapById", vnLabOrderId, request, response);
        return response;
    }

    @Override
    @Transactional
    public void deleteVnLabOrderIcdMap(Long vnLabOrderId) {
        log.debug("Entering deleteVnLabOrderIcdMap | vnLabOrderId: {}", vnLabOrderId);
        List<VnLabOrderIcdMap> icdMaps = repository.findByVnLabOrder_TranId(vnLabOrderId);

        if (icdMaps.isEmpty())
            throw new ResourceNotFoundException("Vn_LabOrder_Icd_Map", vnLabOrderId);

        repository.deleteAll(icdMaps);
        logResult("VnLabOrderIcdMap Deleted", "deleteVnLabOrderIcdMap", vnLabOrderId, null, null);

    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    void logResult(String action, String methodName, Long vnLabOrderId, Object input, Object result) {
        String logString = "Vn LabOrder Mst: " + action + " | " + methodName + " | vnLabOrderId: " + vnLabOrderId;

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
