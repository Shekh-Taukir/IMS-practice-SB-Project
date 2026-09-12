package com.tsTech.practice.IMS_v2.visitNote.diagnosis.service;

import com.tsTech.practice.IMS_v2.common.exception.BusinessValidationException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.repository.DiagnosisIcdMapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

/// //////////////////////////////////////////
//
// Name: Diagnosis Icd Map Common Utility
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 09, 2026 || TaukirHp (ER 1020 - vn careplan icd map entity coding)
/// //////////////////////////////////////////

@Slf4j
@RequiredArgsConstructor
public class DiagnosisIcdMapUtils {

    private final DiagnosisIcdMapRepository diagnosisIcdMapRepository;

    public void updateDiagnosisForNewIcds(Set<Long> newIcdSet, Long pnId){
        log.debug("Entering updateDiagnosisForNewIcds() for pnId: {}", pnId);

        List<Long> visitIcdList = diagnosisIcdMapRepository.getDiagnosisIcdListByPnId(pnId);

        if(visitIcdList.isEmpty())
            throw new ResourceNotFoundException("Diagnosis", "for pnId: "+pnId);

        List<Long> newIcdForDiagnosis = newIcdSet
                .stream()
                .filter(x->!visitIcdList.contains(x))
                .toList();

        if(newIcdForDiagnosis.isEmpty())
            return;

        if(visitIcdList.size()+newIcdForDiagnosis.size()>8)
            throw new BusinessValidationException("Only 8 Icds are allowed in Diagnosis, for a visit", "ICD_LIMIT_EXCEEDED");

    }
}
