package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service.impl;

import com.tsTech.practice.IMS_v2.common.exception.InvalidPatchRequestException;
import com.tsTech.practice.IMS_v2.visitNote.core.entities.VisitNote;
import com.tsTech.practice.IMS_v2.visitNote.core.repository.VisitNoteRepository;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.entity.LabOrder;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.repository.LabOrderRepository;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.response.VnLabOrderResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity.VnLabOrder;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.mapper.VnLabOrderMapper;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.repository.VnLabOrderRepository;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service.VnLabOrderService;
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
// Name: VisitNote LabOrder Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : New Func || Sep 08, 2026 || TaukirHp (ER 1020 - vn lab order entity coding)
// v1.1 || type : Change || Sep 11, 2026 || TaukirS (ER 1020 - vn lab order entity coding)

/// //////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VnLabOrderServiceImpl implements VnLabOrderService {

    private final VnLabOrderRepository repository;
    private final VisitNoteRepository visitNoteRepository;
    private final LabOrderRepository labOrderRepository;

    private final VnLabOrderMapper mapper;


    @Override
    @Transactional
    public VnLabOrderResponse createVnLabOrder(Long pnId, VnLabOrderRequest request) {
        log.debug("Entering createVnLabOrder | pnId: {}", pnId);
        //checks that labOrder exists for following pnId or not, if exists then throws DuplicateEntity exception
        repository.checkLabOrderAndVisitExists(pnId, request.labOrderId());
        VisitNote visitNote = visitNoteRepository.getVisitNoteEntityById(pnId);
        LabOrder labOrder = labOrderRepository.getEntityById(request.labOrderId());

        VnLabOrder vnLabOrder = mapper.fromRequestToEntity(request);
        vnLabOrder.setVisitNote(visitNote);
        vnLabOrder.setLabOrder(labOrder);

        VnLabOrderResponse response = mapper.toResponse(
                repository.save(vnLabOrder)
        );
        logResult("New VnLabOrder created", "createVnLabOrder", pnId, null, request, response);
        return response;
    }

    @Override
    public VnLabOrderResponse getVnLabOrderById(Long pnId, Long vnLabOrderId) {
        log.debug("Entering getVnLabOrderById | pnId: {}, | vnLabOrderId : {}", pnId, vnLabOrderId);

        VnLabOrderResponse response = mapper.toResponse(
                repository.getEntityById(pnId, vnLabOrderId)
        );
        logResult("Retrieved VnLabOrder by id", "getVnLabOrderById", pnId, vnLabOrderId, null, response);
        return response;
    }

    @Override
    public List<VnLabOrderResponse> getVnLabOrderList(Long pnId) {
        log.debug("Entering getVnLabOrderList | pnId: {}", pnId);
        visitNoteRepository.checkVisitNoteExistsById(pnId);
        List<VnLabOrderResponse> response = mapper.toResponseList(
                repository.findByVisitNote_TranIdOrderByTranIdDesc(pnId)
        );
        logResult("Retrieved VnLabOrder List", "getVnLabOrderList", pnId, null, null, response);
        return response;
    }

    @Override
    @Transactional
    public VnLabOrderResponse updateVnLabOrderById(Long pnId, Long vnLabOrderId, VnLabOrderRequest request) {
        log.debug("Entering updateVnLabOrderById | pnId: {} ", pnId);
        VnLabOrder vnLabOrder = repository.getEntityById(pnId, vnLabOrderId);

        if (!request.labOrderId().equals(vnLabOrder.getLabOrder().getTranId())) {
            LabOrder labOrder = labOrderRepository.getEntityById(request.labOrderId());
            vnLabOrder.setLabOrder(labOrder);
        }

        mapper.updateEntityFromRequest(request, vnLabOrder);
        VnLabOrderResponse response = mapper.toResponse(
                repository.save(vnLabOrder)
        );
        logResult("Updated VnLabOrder", "updateVnLabOrderById", pnId, vnLabOrderId, request, response);
        return response;
    }

    @Override
    @Transactional
    public void deleteVnLabOrderById(Long pnId, Long vnLabOrderId) {
        log.debug("Entering deleteVnLabOrderById | pnId: {} | vnLabOrderId: {} ", pnId, vnLabOrderId);
        VnLabOrder vnLabOrder = repository.getEntityById(pnId, vnLabOrderId);

        repository.delete(vnLabOrder);
        logResult("delete vnLabOrder ", "deleteVnLabOrderById", pnId, vnLabOrderId, null, null);
    }

    @Override
    @Transactional
    public VnLabOrderResponse patchVnLabOrderById(Long pnId, Long vnLabOrderId, VnLabOrderPatchRequest request) {
        log.debug("Entering patchVnLabOrderById | pnId: {} | vnLabOrderId: {} ", pnId, vnLabOrderId);
        validatePatchRequest(request);

        JsonNullable<Long> requestLabOrderId = request.labOrderId();
        VnLabOrder vnLabOrder = repository.getEntityById(pnId, vnLabOrderId);
        mapper.patch(request, vnLabOrder);

        if (mapper.isPresent(requestLabOrderId)) {
            Long newLabOrderId = mapper.unwrapLong(requestLabOrderId);

            if (!newLabOrderId.equals(vnLabOrder.getLabOrder().getTranId()))
                vnLabOrder.setLabOrder(
                        labOrderRepository.getEntityById(newLabOrderId)
                );

        }
        VnLabOrderResponse response = mapper.toResponse(
                repository.save(vnLabOrder)
        );

        logResult("VnLabOrder partially updated", "patchVnLabOrderById", pnId, vnLabOrderId, request, response);
        return response;
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    void logResult(String action, String methodName, Long pnId, Long vnLabOrderId, Object input, Object result) {
        String logString = "VnLabOrder : " + action + " | " + methodName + " | pnId: " + pnId;

        if (vnLabOrderId != null)
            logString += " | labOrderId: " + vnLabOrderId;

        log.debug(logString);

        if (log.isTraceEnabled()) {
            if (input != null)
                logString += " | Input Data: " + input;

            if (result != null)
                logString += " | Result Data: " + result;

            log.trace(logString);
        }

    }

    void validatePatchRequest(VnLabOrderPatchRequest request) {
        Map<String, String> violations = new HashMap<>();
        JsonNullable<Long> requestLabOrderId = request.labOrderId();

        if (mapper.isPresent(requestLabOrderId) &&
                mapper.unwrapLong(requestLabOrderId) == null) {
            violations.put("labOrderId", "LabOrderId cannot be null if provided");
        }

        if (!violations.isEmpty())
            throw new InvalidPatchRequestException(violations);
    }
}
