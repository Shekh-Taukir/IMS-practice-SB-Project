package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.service.impl;

import com.tsTech.practice.IMS_v2.common.exception.DuplicateResourceException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.setup.icd.entities.ICD;
import com.tsTech.practice.IMS_v2.setup.icd.repository.IcdRepository;
import com.tsTech.practice.IMS_v2.setup.icd.service.IcdComUtils;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisIcdMapRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.projection.VnCareplanIcdMapProjection;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanIcdMapRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.response.VnCareplanIcdMapResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.response.VnCareplanResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.entity.VnCareplan;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.entity.VnCareplanIcdMap;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.mapper.VnCareplanIcdMapMapper;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.repository.VnCareplanIcdMapRepository;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.repository.VnCareplanRepository;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.service.VnCareplanIcdMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/// //////////////////////////////////////////
//
// Name: VN Careplan Icd Map Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 08, 2026 || TaukirS (ER 1021 - vn careplan icd map entity coding)
// v1.2 || type : Change || Sep 09, 2026 || TaukirHp (ER 1020 - vn careplan icd map entity coding)
/// //////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VnCareplanIcdMapServiceImpl implements VnCareplanIcdMapService {

    private final VnCareplanIcdMapRepository repository;
    private final VnCareplanRepository vnCareplanRepository;
    private final IcdRepository icdRepository;

    private final VnCareplanIcdMapMapper mapper;

    @Override
    @Transactional
    public List<VnCareplanIcdMapResponse> createVnCareplanIcdMap(Long vnCareplanId, VnCareplanIcdMapRequest request) {
        log.debug("Entering createVnCareplanIcdMap | vnCareplanId: {}", vnCareplanId);

        VnCareplan vnCareplan = vnCareplanRepository.getEntityById(vnCareplanId);
        IcdComUtils.checkDuplicateSeqAndIcdInRequest(request.icdItemList());

        if (repository.existsByVnCareplan_TranId(vnCareplanId))
            throw new DuplicateResourceException("DUPLICATE_VN_CAREPLAN_ICD_MAP", "Icd Mapping already exists for vnCareplanId: " + vnCareplanId);

        //Sep 09, 2026 TaukirHp (ER 1020 - vn lab order entity coding)
        //created a function as this map creation will get used in update api as well
        Map<Long, Long> requestIcdMap = getRequstIcdMap(request);

        Map<Long, ICD> newIcdMap = IcdComUtils.getNewIcdsFromReq(requestIcdMap.keySet(), icdRepository);

        List<VnCareplanIcdMap> newCareplanIcdMap = newIcdMap
                .entrySet()
                .stream()
                .map(icdMap -> VnCareplanIcdMap
                        .builder()
                        .icd(icdMap.getValue())
                        .seq(requestIcdMap.get(icdMap.getKey()))
                        .vnCareplan(vnCareplan)
                        .build())
                .toList();

        List<VnCareplanIcdMapResponse> response = mapper.toResponseList(
                repository.saveAll(newCareplanIcdMap)
        );

        logResult("VnCareplanIcdMap Created", "createVnCareplanIcdMap", vnCareplanId, request, response);
        return response;
    }


    @Override
    public List<VnCareplanIcdMapResponse> getVnCareplanIcdMapList(Long vnCareplanId) {
        //Start Sep 10, 2026 TaukirHp (ER 1021 - vn careplan icd map entity coding)
        log.debug("Entering getVnCareplanIcdMapList | vnCareplanId: {}", vnCareplanId);

        List<VnCareplanIcdMapProjection> icdMapProjections = repository.getVnCareplanIcdProjection(vnCareplanId);

        List<VnCareplanIcdMapResponse> response = mapper.fromProjectionToResponse(icdMapProjections);
        logResult("VnCareplanIcdMap List Retrieved", "getVnCareplanIcdMapList", vnCareplanId, null, response);
        return response;
        //End Sep 10, 2026 TaukirHp (ER 1021 - vn careplan icd map entity coding)
    }

    @Override
    @Transactional
    public List<VnCareplanIcdMapResponse> updateVnCareplanIcdMapById(Long vnCareplanId, VnCareplanIcdMapRequest request) {
        //Start Sep 10, 2026 TaukirHp (ER 1021 - vn careplan icd map entity coding)
        log.debug("Entering updateVnCareplanIcdMap | vnCareplanId: {}", vnCareplanId);
        VnCareplan vnCareplan = vnCareplanRepository.getEntityById(vnCareplanId);
        IcdComUtils.checkDuplicateSeqAndIcdInRequest(request.icdItemList());
        Map<Long, Long> requestIcdMap = getRequstIcdMap(request);

        Map<Long, VnCareplanIcdMap> icdMaps = repository
                .findByVnCareplan_TranId(vnCareplanId)
                .stream()
                .collect(Collectors.toMap(
                        x->x.getIcd().getTranId(),
                        Function.identity()));

        // get the list of icds which needs to be deleted : (existing - request) icd list
        Set<Long> toBeDeletedIcdSet = icdMaps
                .keySet()
                .stream()
                .filter(x->!requestIcdMap.containsKey(x))
                .collect(Collectors.toSet());

        //after getting list, remove it from icdMap, and collect a list so that it can be directly passed to deleteAll()
        List<VnCareplanIcdMap> toBeDeletedIcdList = toBeDeletedIcdSet
                .stream()
                .map(icdMaps::remove)
                .toList();

        requestIcdMap
                .forEach((newIcdId, newSeq)->{
                    if((icdMaps.containsKey(newIcdId)) &&
                            !icdMaps.get(newIcdId).getSeq().equals(newSeq)){
                            icdMaps.get(newIcdId).setSeq(newSeq);
                    }
                });

        //get list of icds which are new in request, and doesn't exists in current list
        Set<Long> newIcdSet = requestIcdMap
                .keySet()
                .stream()
                .filter(x->!icdMaps.containsKey(x))
                .collect(Collectors.toSet());

        Map<Long, ICD> newIcdMap = IcdComUtils.getNewIcdsFromReq(newIcdSet, icdRepository);

        //loops through the new icd's and add the VnCareplanIcd to list.
        newIcdMap.forEach((key, value)->{
            icdMaps.put(key, VnCareplanIcdMap
                    .builder()
                    .seq(requestIcdMap.get(key))
                    .icd(value)
                    .vnCareplan(vnCareplan)
                    .build()
            );
        });

        repository.deleteAll(toBeDeletedIcdList);
        List<VnCareplanIcdMapResponse> response = mapper.toResponseList(
                repository.saveAll(icdMaps.values())
        );

        logResult("Update VnCareplanICdMaps ", "updateVNCareplanICdMapById", vnCareplanId, request, response);
        return response;
        //End Sep 10, 2026 TaukirHp (ER 1021 - vn careplan icd map entity coding)
    }

    @Override
    @Transactional
    public void deleteVnCareplanIcdMap(Long vnCareplanId) {
        log.debug("Entering deleteVnCareplanIcdMap | vnCareplanId: {}", vnCareplanId);
        List<VnCareplanIcdMap> icdMaps = repository.findByVnCareplan_TranId(vnCareplanId);

        if (icdMaps.isEmpty())
            throw new ResourceNotFoundException("Vn_Careplan_Icd_Map", vnCareplanId);

        repository.deleteAll(icdMaps);
        logResult("VnCareplanIcdMap Deleted", "deleteVnCareplanIcdMap", vnCareplanId, null, null);
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    void logResult(String action, String methodName, Long vnCareplanId, Object input, Object result) {
        String logString = "Careplan Mst: " + action + " | " + methodName + " | vnCarePlanId: " + vnCareplanId;

        log.debug(logString);

        if (log.isTraceEnabled()) {
            if (input != null)
                logString += " | Input Data: " + input;

            if (result != null)
                logString += " | Result Data: " + result;

            log.trace(logString);
        }

    }

    //Start Sep 09, 2026 TaukirHp (ER 1020 - vn careplan icd map entity coding)
    private Map<Long, Long> getRequstIcdMap(VnCareplanIcdMapRequest request) {
        return request
                .icdItemList()
                .stream()
                .collect(Collectors.toMap(
                        DiagnosisIcdMapRequest::icdId,
                        DiagnosisIcdMapRequest::seq));
    }
    //End Sep 09, 2026 TaukirHp (ER 1020 - vn careplan icd map entity coding)

}
