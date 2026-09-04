package com.tsTech.practice.IMS_v2.visitNote.core.service.impl;

import com.tsTech.practice.IMS_v2.common.exception.BusinessValidationException;
import com.tsTech.practice.IMS_v2.office.entities.Provider;
import com.tsTech.practice.IMS_v2.office.repository.ProviderRepository;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import com.tsTech.practice.IMS_v2.patient.repository.PatientRepository;
import com.tsTech.practice.IMS_v2.setup.visitType.entities.VisitType;
import com.tsTech.practice.IMS_v2.setup.visitType.repository.VisitTypeRepository;
import com.tsTech.practice.IMS_v2.visitNote.core.dto.request.VisitNoteRequest;
import com.tsTech.practice.IMS_v2.visitNote.core.dto.response.VisitNoteResponse;
import com.tsTech.practice.IMS_v2.visitNote.core.entities.VisitNote;
import com.tsTech.practice.IMS_v2.visitNote.core.mapper.VisitNoteMapper;
import com.tsTech.practice.IMS_v2.visitNote.core.repository.VisitNoteRepository;
import com.tsTech.practice.IMS_v2.visitNote.core.service.VisitNoteService;
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
// Name: VisitNote Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 20, 2026 || TaukirS (ER 1015 - visitnote entity coding)

/////////////////////////////////////////////

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitNoteServiceImpl implements VisitNoteService {

    private final VisitNoteRepository visitNoteRepository;
    private final VisitNoteMapper visitNoteMapper;
    private final PatientRepository patientRepository;
    private final ProviderRepository providerRepository;
    private final VisitTypeRepository visitTypeRepository;

    @Override
    @Transactional
    public VisitNoteResponse createVisit(VisitNoteRequest request) {
        log.debug("Entering createVisit()");
        Patient patient = patientRepository.getPatientEntityById(request.patientId());
        Provider provider = providerRepository.getEntityById(request.providerId());
        VisitNote visitNote = visitNoteMapper.fromRequestToEntity(request);
        VisitType visitType = visitTypeRepository.getEntityById(request.visitTypeId());

        visitNote.setPatient(patient);
        visitNote.setProvider(provider);
        visitNote.setOffice(provider.getOffice());
        visitNote.setVisitType(visitType);

        VisitNoteResponse response = visitNoteMapper.toResponse(visitNoteRepository.save(visitNote));

        logResult("New Visit created", "createVisit", request.patientId(), null, request, response);
        return response;
    }

    @Override
    public VisitNoteResponse getVisitById(Long patientId, Long pnId) {
        log.debug("Entering getVisitById() | patientId: {} | pnId: {}", patientId, pnId);
        VisitNoteResponse response = visitNoteMapper.fromProjectionToResponse(
                visitNoteRepository.getVisitNoteProjById(patientId, pnId)
        );

        logResult("Retrieved visit", "getVisitById", patientId, pnId, null, response);
        return response;
    }

    @Override
    public List<VisitNoteResponse> getAllVisits(Long patientId) {
        log.debug("Entering getAllVisits() | patientId: {}", patientId);
        List<VisitNoteResponse> responses = visitNoteMapper.fromProjectionToResponseList(
                visitNoteRepository.findAllVisitNotes(patientId)
        );

        logResult("Retrieved List", "getAllVisits", patientId, null, null, responses);
        return responses;
    }

    @Override
    @Transactional
    public VisitNoteResponse updateVisitById(Long patientId, Long pnId, VisitNoteRequest request) {
        log.debug("Entering updateVisitById() | patientId: {} | pnId: {}", patientId, pnId);

        if (!(request
                .patientId()
                .equals(patientId)))
            throw new BusinessValidationException("Patient Id in url doesn't matches with request body's patient Id", "PATIENT_ID_MISMATCH");
        VisitNote visitNote = visitNoteRepository.getEntityById(patientId, pnId);

        visitNoteMapper.updateEntityFromRequest(request, visitNote);
        updateProviderInVisit(request.providerId(), visitNote);
        updateVisitTypeInVisit(request.visitTypeId(), visitNote);

        VisitNoteResponse response = visitNoteMapper.toResponse(visitNoteRepository.save(visitNote));
        logResult("Visit Note Updated", "updateVisitById", patientId, pnId, request, response);
        return response;
    }

    @Override
    @Transactional
    public void deleteVisitById(Long patientId, Long pnId) {
        log.debug("Entering deleteVisitById() | patientId: {} | pnId: {}", patientId, pnId);
        VisitNote visitNote = visitNoteRepository.getEntityById(patientId, pnId);
        visitNoteRepository.delete(visitNote);
        logResult("Visit deleted", "deleteVisitById", patientId, pnId, null, null);
    }

    @Override
    @Transactional
    public VisitNoteResponse patchUpdateVisitById(Long patientId, Long pnId, Map<String, Object> patchUpdate) {
        log.debug("Entering patchUpdateVisitById() | patientId: {} | pnId: {}", patientId, pnId, patchUpdate);
        VisitNote visitNote = visitNoteRepository.getEntityById(patientId, pnId);

        patchUpdate.forEach((key, value) -> {
            switch (key) {
                case "providerId":
                    updateProviderInVisit(Long.valueOf(value.toString()), visitNote);
                    break;

                case "visitTypeId":
                    updateVisitTypeInVisit(Long.valueOf(value.toString()), visitNote);
                    break;

                default:
                    Field field = ReflectionUtils.getRequiredField(VisitNote.class, key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, visitNote, value);
                    break;
            }
        });
        VisitNoteResponse response = visitNoteMapper.toResponse(visitNoteRepository.save(visitNote));
        logResult("Patch update of visit", "patchUpdateVisitById", patientId, pnId, patchUpdate, response);
        return response;
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    void logResult(String action, String methodName, Long patientId, Long pnId, Object inputData, Object resultData) {
        String logString = "VisitNote: " + action + " | " + methodName + " | patientId: " + patientId;

        if (pnId != null)
            logString += " | pnId: " + pnId;

        log.debug(logString);
        if (log.isTraceEnabled()) {

            if (inputData != null)
                logString += " | inputData: " + inputData;

            if (resultData != null)
                logString += " | resultData: " + resultData;

            log.trace(logString);
        }
    }

    private void updateProviderInVisit(Long providerId, VisitNote visitNote) {
        if (providerId != null && !(visitNote
                .getProvider()
                .getTranId()
                .equals(providerId))) {
            log.debug("Updating new provider & office in visit note");
            Provider provider = providerRepository.getEntityById(providerId);
            visitNote.setProvider(provider);
            visitNote.setOffice(provider.getOffice());
        }
    }

    private void updateVisitTypeInVisit(Long visitTypeId, VisitNote visitNote) {
        if (visitTypeId != null && !(visitNote
                .getVisitType()
                .getTranId()
                .equals(visitTypeId))) {
            log.debug("Updating new visitType in visit note");
            VisitType visitType = visitTypeRepository.getEntityById(visitTypeId);
            visitNote.setVisitType(visitType);
        }
    }
}
