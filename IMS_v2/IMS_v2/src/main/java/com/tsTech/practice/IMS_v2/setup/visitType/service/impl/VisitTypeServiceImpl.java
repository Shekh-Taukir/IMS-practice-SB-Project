package com.tsTech.practice.IMS_v2.setup.visitType.service.impl;

import com.tsTech.practice.IMS_v2.setup.visitType.dto.request.VisitTypeRequest;
import com.tsTech.practice.IMS_v2.setup.visitType.dto.response.VisitTypeResponse;
import com.tsTech.practice.IMS_v2.setup.visitType.entities.VisitType;
import com.tsTech.practice.IMS_v2.setup.visitType.mapper.VisitTypeMapper;
import com.tsTech.practice.IMS_v2.setup.visitType.repository.VisitTypeRepository;
import com.tsTech.practice.IMS_v2.setup.visitType.service.VisitTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/// //////////////////////////////////////////
//
// Name: Visit Type Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 15, 2026 || TaukirS (ER 1012 - visit type entity code)

/////////////////////////////////////////////

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VisitTypeServiceImpl implements VisitTypeService {

    private final VisitTypeRepository visitTypeRepository;
    private final VisitTypeMapper visitTypeMapper;

    @Override
    @Transactional
    public VisitTypeResponse createVisitType(VisitTypeRequest request) {
        log.debug("Entering createVisitType()");

        visitTypeRepository.entityExistsByName(request.name());

        VisitType visitType = visitTypeMapper.fromRequestToEntity(request);
        VisitTypeResponse visitTypeResponse = visitTypeMapper.toResponse(visitTypeRepository.save(visitType));

        logResult("creating", "createVisitType", null, request, visitTypeResponse);
        return visitTypeResponse;
    }

    @Override
    public VisitTypeResponse getVisitTypeById(Long visitTypeId) {
        log.debug("Entering getVisitTypeById() | id: {}", visitTypeId);

        VisitType visitType = visitTypeRepository.getEntityById(visitTypeId);
        VisitTypeResponse response = visitTypeMapper.toResponse(visitType);

        logResult("getById", "getVisitTypeById", visitTypeId, null, response);
        return response;
    }

    @Override
    public List<VisitTypeResponse> getVisitTypeList() {
        log.debug("Entering getVisitTypeList()");

        List<VisitTypeResponse> visitTypeResponseList = visitTypeMapper
                .toResponseList(
                        visitTypeRepository.findAll()
                );

        logResult("getList", "getVisitTypeList", null, null, visitTypeResponseList);
        return visitTypeResponseList;
    }

    @Override
    @Transactional
    public VisitTypeResponse updateVisitType(Long visitTypeId, VisitTypeRequest request) {
        log.debug("Entering updateVisitType() for id: {}", visitTypeId);

        VisitType visitType = visitTypeRepository.getEntityById(visitTypeId);
        if (!visitType
                .getName()
                .equalsIgnoreCase(request.name()))
            visitTypeRepository.entityExistsByName(request.name());

        visitTypeMapper.updateEntityFromRequest(request, visitType);
        visitType = visitTypeRepository.save(visitType);
        VisitTypeResponse response = visitTypeMapper.toResponse(visitType);

        logResult("UpdateById", "updateVisitType", visitTypeId, request, response);
        return response;
    }

    @Override
    @Transactional
    public void deleteVisitType(Long visitTypeId) {
        log.debug("Entering deleteVisitType for id: {}", visitTypeId);

        VisitType visitType = visitTypeRepository.getEntityById(visitTypeId);
        visitTypeRepository.delete(visitType);
        logResult("DeleteById", "deleteVisitType", visitTypeId, null, null);
    }

    @Override
    @Transactional
    public VisitTypeResponse patchUpdateVisitType(Long visitTypeId, Map<String, Object> patchData) {
        log.debug("Entering patchUpdateVisitType for id: {}", visitTypeId);

        VisitType visitType = visitTypeRepository.getEntityById(visitTypeId);

        patchData.forEach((key, value) -> {
            Field field = ReflectionUtils.getRequiredField(VisitType.class, key);
            field.setAccessible(true);

            if (key.equals("name") && !visitType
                    .getName()
                    .equalsIgnoreCase(value.toString()))
                visitTypeRepository.entityExistsByName(value.toString());

            ReflectionUtils.setField(field, visitType, value);
        });
        VisitTypeResponse response = visitTypeMapper.toResponse(visitTypeRepository.save(visitType));

        logResult("PatchUpdateById", "patchUpdateVisitType", visitTypeId, patchData, response);
        return response;
    }


    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    private void logResult(String action, String methodName, Long visitTypeId, Object inputData, Object resultData) {
        String logString = "Visit Type : " + action + " | " + methodName + "()";

        if (visitTypeId != null)
            logString += " | visitTypeId: " + visitTypeId;

        log.debug(logString);

        if (log.isTraceEnabled()) {
            if (inputData != null)
                logString += " | input: " + inputData;

            if (resultData != null)
                logString += " | result: " + resultData;

            log.trace(logString);
        }
    }
}
