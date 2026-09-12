package com.tsTech.practice.IMS_v2.visitNote.diagnosis.service;

import com.tsTech.practice.IMS_v2.common.exception.BusinessValidationException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.setup.icd.entities.ICD;
import com.tsTech.practice.IMS_v2.setup.icd.repository.IcdRepository;
import com.tsTech.practice.IMS_v2.setup.icd.service.IcdComUtils;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.entity.Diagnosis;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.entity.DiagnosisIcdMap;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.repository.DiagnosisIcdMapRepository;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.repository.DiagnosisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/// //////////////////////////////////////////
//
// Name: Diagnosis Icd Map Common Utility
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 09, 2026 || TaukirHp (ER 1021 - vn careplan icd map entity coding)
// v1.1 || type : Change || Sep 12, 2026 || TaukirS (ER 1021 - vn careplan icd map entity coding)

/// //////////////////////////////////////////

@Slf4j
@RequiredArgsConstructor
@Component
public class DiagnosisIcdMapUtils {

    private final DiagnosisIcdMapRepository diagnosisIcdMapRepository;
    private final IcdRepository icdRepository;
    private final DiagnosisRepository diagnosisRepository;

    public void updateDiagnosisForNewIcds(Set<Long> newIcdSet, Long pnId) {
        log.debug("Entering updateDiagnosisForNewIcds() for pnId: {}", pnId);
        //this give the list of icds which falls under pnId
        List<Long> visitIcdList = diagnosisIcdMapRepository.getDiagnosisIcdListByPnId(pnId);

        //this states that Diagnosis doesn't exist for this visit
        if (visitIcdList.isEmpty())
            throw new ResourceNotFoundException("Diagnosis", "for pnId: " + pnId);

        //filters diagnosis list to get which icds are not present in diagnosis, and needs to be added
        Set<Long> newIcdForDiagnosis = newIcdSet
                .stream()
                .filter(x -> !visitIcdList.contains(x))
                .collect(Collectors.toSet());

        if (newIcdForDiagnosis.isEmpty())
            return;

        if (visitIcdList.size() + newIcdForDiagnosis.size() > 8)
            throw new BusinessValidationException("Only 8 Icds are allowed in Diagnosis, per visit", "ICD_LIMIT_EXCEEDED");

        addNewIcdsInDiagnosis(newIcdForDiagnosis, pnId, visitIcdList.size());
        log.debug("Completed : updateDiagnosisForNewIcds() for pnId: {}", pnId);
    }

    private void addNewIcdsInDiagnosis(Set<Long> newIcdForDiagnosis, Long pnId, int icdListSize) {
        log.debug("Entering addNewIcdsInDiagnosis() for pnId: {}", pnId);
        Map<Long, ICD> newIcdMap = IcdComUtils.getNewIcdsFromReq(newIcdForDiagnosis, icdRepository);

        Diagnosis diagnosis = diagnosisRepository.getEntityByPnId(pnId);
        AtomicInteger newSeq = new AtomicInteger(icdListSize + 1);

        List<DiagnosisIcdMap> newDiagIcdMapList = newIcdForDiagnosis
                .stream()
                .map(newIcdId -> DiagnosisIcdMap
                        .builder()
                        .diagnosis(diagnosis)
                        .icd(newIcdMap.get(newIcdId))
                        .seq((long) newSeq.getAndIncrement())
                        .build()
                ).toList();

        diagnosisIcdMapRepository.saveAll(newDiagIcdMapList);
        log.debug("Completed : addNewIcdsInDiagnosis() for pnId: {}", pnId);
    }
}
