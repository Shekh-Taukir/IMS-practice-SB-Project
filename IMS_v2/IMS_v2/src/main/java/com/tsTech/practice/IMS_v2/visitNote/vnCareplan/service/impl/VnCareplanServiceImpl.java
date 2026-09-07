package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.service.impl;

import com.tsTech.practice.IMS_v2.common.exception.InvalidPatchRequestException;
import com.tsTech.practice.IMS_v2.setup.careplan.entity.Careplan;
import com.tsTech.practice.IMS_v2.setup.careplan.repository.CareplanRepository;
import com.tsTech.practice.IMS_v2.visitNote.core.entities.VisitNote;
import com.tsTech.practice.IMS_v2.visitNote.core.repository.VisitNoteRepository;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.response.VnCareplanResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.entity.VnCareplan;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.mapper.VnCareplanMapper;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.repository.VnCareplanRepository;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.service.VnCareplanService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// //////////////////////////////////////////
//
// Name: VisitNote Careplan Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1018 - visit careplan entity coding)

/// //////////////////////////////////////////


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VnCareplanServiceImpl implements VnCareplanService {

    private final VnCareplanRepository vnCareplanRepository;
    private final VisitNoteRepository visitNoteRepository;
    private final CareplanRepository careplanRepository;

    private final VnCareplanMapper vnCareplanMapper;

    @Override
    @Transactional
    public VnCareplanResponse createVnCareplan(Long pnId, VnCareplanRequest request) {
        log.debug("Entering createVnCareplan | pnId: {}", pnId);

        //checks that careplan exists for following pnId or not, if exists then throws DuplicateEntity exception
        vnCareplanRepository.checkVisitAndCareplanExists(pnId, request.careplanId());

        VisitNote visitNote = visitNoteRepository.getVisitNoteEntityById(pnId);
        Careplan careplan = careplanRepository.getEntityById(request.careplanId());
        VnCareplan vnCareplan = vnCareplanMapper.fromRequestToEntity(request);

        vnCareplan.setVisitNote(visitNote);
        vnCareplan.setCareplan(careplan);

        VnCareplanResponse response = vnCareplanMapper.toResponse(
                vnCareplanRepository.save(vnCareplan)
        );
        logResult("New VnCareplan created", "createVnCareplan", pnId, null, request, response);
        return response;
    }

    @Override
    public VnCareplanResponse getVnCareplanById(Long pnId, Long vnCareplanId) {
        log.debug("Entering getVnCareplanById | pnId: {}, | vnCareplanId : {}", pnId, vnCareplanId);

        VnCareplanResponse response = vnCareplanMapper.toResponse(
                vnCareplanRepository.getEntityById(pnId, vnCareplanId)
        );
        logResult("Retrieved vnCareplan by id", "getVnCareplanById", pnId, vnCareplanId, null, response);
        return response;
    }

    @Override
    public List<VnCareplanResponse> getVnCareplanList(Long pnId) {
        log.debug("Entering getVnCareplanList | pnId: {}", pnId);
        visitNoteRepository.checkVisitNoteExistsById(pnId);
        List<VnCareplanResponse> response = vnCareplanMapper.toResponseList(
                vnCareplanRepository.findByVisitNote_TranIdOrderByTranIdDesc(pnId)
        );
        logResult("Retrieved vnCareplan List", "getVnCareplanList", pnId, null, null, response);
        return response;
    }

    @Override
    @Transactional
    public VnCareplanResponse updateVnCareplanById(Long pnId, Long vnCareplanId, VnCareplanRequest request) {
        log.debug("Entering updateVnCareplanById | pnId: {} ", pnId);
        VnCareplan vnCareplan = vnCareplanRepository.getEntityById(pnId, vnCareplanId);

        if (!request
                .careplanId()
                .equals(vnCareplan
                        .getCareplan()
                        .getTranId())
        ) {
            vnCareplan.setCareplan(
                    careplanRepository.getEntityById(request.careplanId())
            );
        }

        vnCareplanMapper.updateEntityFromRequest(request, vnCareplan);
        VnCareplanResponse response = vnCareplanMapper.toResponse(
                vnCareplanRepository.save(vnCareplan)
        );

        logResult("Retrieved vnCareplan List", "getVnCareplanList", pnId, null, null, response);
        return response;
    }

    @Override
    @Transactional
    public void deleteVnCareplanById(Long pnId, Long vnCareplanId) {
        log.debug("Entering deleteVnCareplanById | pnId: {} | vnCareplanId: {} ", pnId, vnCareplanId);
        VnCareplan vnCareplan = vnCareplanRepository.getEntityById(pnId, vnCareplanId);

        vnCareplanRepository.delete(vnCareplan);
        logResult("VnCareplan deleted", "deleteVnCareplanById", pnId, vnCareplanId, null, null);
    }

    @Override
    @Transactional
    public VnCareplanResponse patchVnCareplanById(Long pnId, Long vnCareplanId, VnCareplanPatchRequest request) {
        log.debug("Entering patchVnCareplanById | pnId: {} | vnCareplanId: {} ", pnId, vnCareplanId);
        JsonNullable<Long> requestCareplanId = request.careplanId();

        //validates the patch request data, if given by user, then valid or not
        validatePatchRequest(request);
        VnCareplan vnCareplan = vnCareplanRepository.getEntityById(pnId, vnCareplanId);
        vnCareplanMapper.patch(request, vnCareplan);

        if (vnCareplanMapper.isPresent(requestCareplanId)) {
            Long newCareplanId = vnCareplanMapper.unwrapLong(requestCareplanId);

            if (newCareplanId != null && !(newCareplanId.equals(vnCareplan.getCareplan().getTranId()))) {
                Careplan careplan = careplanRepository.getEntityById(newCareplanId);
                vnCareplan.setCareplan(careplan);
            }
        }

        VnCareplanResponse response = vnCareplanMapper.toResponse(
                vnCareplanRepository.save(vnCareplan)
        );

        logResult("VnCareplan partially updated", "patchVnCareplanById", pnId, vnCareplanId, request, response);
        return response;
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    void logResult(String action, String methodName, Long pnId, Long vnCareplanId, Object input, Object result) {
        String logString = "Careplan Mst: " + action + " | " + methodName + " | pnId: " + pnId;

        if (vnCareplanId != null)
            logString += " | carePlanId: " + vnCareplanId;

        log.debug(logString);

        if (log.isTraceEnabled()) {
            if (input != null)
                logString += " | Input Data: " + input;

            if (result != null)
                logString += " | Result Data: " + result;

            log.trace(logString);
        }

    }

    void validatePatchRequest(VnCareplanPatchRequest request) {
        Map<String, String> violations = new HashMap<>();
        JsonNullable<Long> requestCareplanId = request.careplanId();
        JsonNullable<Long> reqSeq = request.seq();

        if (vnCareplanMapper.isPresent(requestCareplanId) &&
                vnCareplanMapper.unwrapLong(requestCareplanId) == null) {
            violations.put("careplanId", "CareplanId cannot be null if provided");
        }

        if (vnCareplanMapper.isPresent(reqSeq) && vnCareplanMapper.unwrapLong(reqSeq) <= 0)
            violations.put("seq", "Seq of VN careplan must be positive");

        if (!violations.isEmpty())
            throw new InvalidPatchRequestException(violations);
    }
}
