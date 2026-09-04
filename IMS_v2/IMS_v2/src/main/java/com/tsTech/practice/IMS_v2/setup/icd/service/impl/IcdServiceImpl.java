package com.tsTech.practice.IMS_v2.setup.icd.service.impl;

import com.tsTech.practice.IMS_v2.setup.icd.dto.request.IcdBulkRequest;
import com.tsTech.practice.IMS_v2.setup.icd.dto.request.IcdRequest;
import com.tsTech.practice.IMS_v2.setup.icd.dto.response.IcdResponse;
import com.tsTech.practice.IMS_v2.setup.icd.dto.response.IcdSearchResponse;
import com.tsTech.practice.IMS_v2.setup.icd.entities.ICD;
import com.tsTech.practice.IMS_v2.setup.icd.mapper.IcdMapper;
import com.tsTech.practice.IMS_v2.setup.icd.repository.IcdRepository;
import com.tsTech.practice.IMS_v2.setup.icd.service.IcdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/// //////////////////////////////////////////
//
// Name: Icd Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 17, 2026 || TaukirS (ER 1014 - icd entity setup coding)
// v1.2 || type : Change || Aug 22, 2026 || TaukirS (ER 1016 - diagnosis entity coding)

/// //////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IcdServiceImpl implements IcdService {

    private final IcdRepository icdRepository;
    private final IcdMapper icdMapper;

    @Override
    @Transactional
    public IcdResponse createIcd(IcdRequest request) {
        log.debug("Entering createIcd()");
        icdRepository.checkIcdCodeExistsOrThrow(request.code());

        ICD icd = icdMapper.fromRequestToEntity(request);
        IcdResponse response = icdMapper.toResponse(icdRepository.save(icd));

        logResult("Create new icd", "createIcd", null, request, response);
        return response;
    }

    //Start Aug 22, 2026 TaukirS (ER 1016 - diagnosis entity coding)
    @Override
    @Transactional
    public List<IcdResponse> createIcdByJsonList(IcdBulkRequest request) {
        log.debug("Entering createIcdByJsonList()");

        List<ICD> icdList = request
                .icdRequestList()
                .stream()
                .map(icdItem -> {
                    icdRepository.checkIcdCodeExistsOrThrow(icdItem.code());
                    return icdMapper.fromRequestToEntity(icdItem);
                })
                .toList();

        List<IcdResponse> response = icdRepository
                .saveAll(icdList)
                .stream()
                .map(icdMapper::toResponse)
                .toList();

        logResult("Create Icd in bulk from JSON List", "createIcdByJsonList", null, request, response);
        return response;
    }
    //End Aug 22, 2026 TaukirS (ER 1016 - diagnosis entity coding)

    @Override
    public IcdResponse getIcdById(Long icdId) {
        log.debug("Entering getIcdById() | icdId: {}", icdId);
        ICD icd = icdRepository.getEntityById(icdId);

        IcdResponse response = icdMapper.toResponse(icd);

        logResult("Retrieved an ICD", "getIcdById", icdId, null, response);
        return response;
    }

    //Start Aug 21, 2026 TaukirS (ER 1016 - diagnosis entity coding)
    //updated the repo function, to add search functionality for icd list api
    @Override
    public List<IcdResponse> getIcdList(String keyword) {
        log.debug("Entering getIcdList()");
        boolean isSearchBlank = (keyword == null || keyword.isBlank());

        List<IcdResponse> response = icdMapper.toResponseList(
                isSearchBlank ?
                        icdRepository.findAll() :
                        icdRepository.findByCodeContainingOrDescriptionContainingIgnoreCase(keyword.toUpperCase(), keyword)
        );

        logResult("Retrieved List", "getIcdList", null, isSearchBlank ? null : "searched for keyword: " + keyword, response);
        return response;
    }

    //added new light-weight search api that returns top 20 based on keyword searched
    @Override
    public List<IcdSearchResponse> getIcdListBySearch(String keyword) {
        log.debug("Entering getIcdListBySearch()");
        boolean isSearchBlank = false;

        if (keyword == null || keyword.isBlank())
            isSearchBlank = true;
        List<IcdSearchResponse> response = icdMapper.toSearchResponseList(isSearchBlank ?
                icdRepository.findTop20ByOrderByCodeAsc() :
                icdRepository.findTop20ByCodeContainingOrDescriptionContainingIgnoreCaseOrderByCodeAsc(keyword.toUpperCase(), keyword)
        );

        logResult("Retrieved List", "getIcdListBySearch", null, isSearchBlank ? null : "searched for keyword: " + keyword, response);
        return response;
    }
    //End Aug 21, 2026 TaukirS (ER 1016 - diagnosis entity coding)

    @Override
    @Transactional
    public IcdResponse updateIcd(Long icdId, IcdRequest request) {
        log.debug("Entering updateIcd() for id: {}", icdId);
        ICD icd = icdRepository.getEntityById(icdId);

        if (!icd
                .getCode()
                .equals(request
                        .code()
                        .trim()
                        .toUpperCase()))
            icdRepository.checkIcdCodeExistsOrThrow(request.code());

        icdMapper.updateEntityFromRequest(request, icd);
        IcdResponse response = icdMapper.toResponse(icdRepository.save(icd));

        logResult("Icd Updated", "updateIcd", icdId, request, response);
        return response;
    }

    @Override
    @Transactional
    public IcdResponse expiryIcdById(Long icdId) {
        log.debug("Entering expiryIcdById() for id: {}", icdId);
        ICD icd = icdRepository.getEntityById(icdId);

        icd.setExpiredAt(LocalDate.now());
        icd.setIsActive(false);
        IcdResponse response = icdMapper.toResponse(icdRepository.save(icd));

        logResult("ICD expired", "expiryIcdById", icdId, null, response);
        return response;
    }

    @Override
    @Transactional
    public IcdResponse patchUpdateIcdById(Long icdId, Map<String, Object> patchData) {
        log.debug("Entering patchUpdateIcdById() for id: {}", icdId);
        ICD icd = icdRepository.getEntityById(icdId);

        patchData.forEach((key, value) -> {
            Field field = ReflectionUtils.getRequiredField(ICD.class, key);
            field.setAccessible(true);

            if (key.equals("code") && !(icd
                    .getCode()
                    .equals(value
                            .toString()
                            .trim()
                            .toUpperCase())))
                icdRepository.checkIcdCodeExistsOrThrow(value.toString());

            ReflectionUtils.setField(field, icd, value);
        });

        IcdResponse response = icdMapper.toResponse(icdRepository.save(icd));
        logResult("Patch Update ICD", "patchUpdateIcdById", icdId, patchData, response);
        return response;
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    void logResult(String action, String methodName, Long icdId, Object inputData, Object resultData) {
        String logString = "ICD Mst: " + action + " | " + methodName + "()";

        if (icdId != null)
            logString += " | icdId: " + icdId;

        log.debug(logString);
        if (log.isTraceEnabled()) {
            if (inputData != null)
                logString += " | inputData: " + inputData;

            if (resultData != null)
                logString += " | resultData: " + resultData;

            log.trace(logString);
        }
    }
}
