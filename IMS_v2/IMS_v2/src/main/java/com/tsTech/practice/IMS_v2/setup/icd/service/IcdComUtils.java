package com.tsTech.practice.IMS_v2.setup.icd.service;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import com.tsTech.practice.IMS_v2.common.exception.DuplicateResourceException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.setup.icd.entities.ICD;
import com.tsTech.practice.IMS_v2.setup.icd.repository.IcdRepository;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisIcdMapRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/// /////////////////////////////////////////////
//
// Name:
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 08, 2026 || TaukirS (ER 1021 - vn careplan icd map entity coding)

/// /////////////////////////////////////////////

public class IcdComUtils {

    public static void checkDuplicateSeqAndIcdInRequest(List<DiagnosisIcdMapRequest> requestList) {
        //Checking that sequence list in request object is not getting repeated
        Set<Long> uniqueSets = new HashSet<>(
                requestList
                        .stream()
                        .map(DiagnosisIcdMapRequest::seq) //Other way to write this: icdItem -> icdItem.seq()
                        .toList()
        );

        if (uniqueSets.size() != requestList
                .size())
            throw new DuplicateResourceException("DUPLICATE_ICD_SEQUENCE", "Duplicate Icd sequence provided in input");

        Set<Long> uniqueIcds = new HashSet<>(
                requestList
                        .stream()
                        .map(DiagnosisIcdMapRequest::icdId) //Other way to write this: icdItem -> icdItem.icdId()
                        .toList()
        );
        if (uniqueIcds.size() != requestList.size())
            throw new DuplicateResourceException("DUPLICATE_ICD_ID", "Duplicate Icd ids provided in input");
    }

    public static Map<Long, ICD> getNewIcdsFromReq(Set<Long> newIcdIdSet, IcdRepository icdRepository) {

        Map<Long, ICD> icdIdMap = icdRepository
                .findAllById(newIcdIdSet)
                .stream()
                .collect(Collectors
                        .toMap(BaseEntity::getTranId, Function.identity())
                );

        List<Long> missingIcdId = newIcdIdSet
                .stream()
                .filter(x -> !icdIdMap.containsKey(x))
                .toList();

        if (!missingIcdId.isEmpty())
            throw new ResourceNotFoundException("ICD", missingIcdId);

        return icdIdMap;
    }
}
