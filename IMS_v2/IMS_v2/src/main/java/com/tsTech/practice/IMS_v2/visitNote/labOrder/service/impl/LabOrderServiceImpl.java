package com.tsTech.practice.IMS_v2.visitNote.labOrder.service.impl;

import com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.request.LabOrderPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.request.LabOrderRequest;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.response.LabOrderResponse;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.entity.LabOrder;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.mapper.LabOrderMapper;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.repository.LabOrderRepository;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.service.LabOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: LabOrder Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 06, 2026 || TaukirS (ER 1019 - lab order entity coding)
/// //////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LabOrderServiceImpl implements LabOrderService {

    private final LabOrderRepository labOrderRepository;
    private final LabOrderMapper labOrderMapper;

    @Override
    @Transactional
    public LabOrderResponse createLabOrder(LabOrderRequest request) {
        log.debug("Entering createLabOrder()");
        LabOrder labOrder = labOrderMapper.fromRequestToEntity(request);

        LabOrderResponse response = labOrderMapper.toResponse(
                labOrderRepository.save(labOrder)
        );
        logResult("Lab Order created", "createLabOrder", null, request, response);
        return response;
    }

    @Override
    public LabOrderResponse getLabOrderById(Long labOrderId) {
        log.debug("Entering getLabOrderById | labOrderId: {}", labOrderId);

        LabOrderResponse response = labOrderMapper.toResponse(
                labOrderRepository.getEntityById(labOrderId)
        );
        logResult("labOrder By Id", "getLabOrderById", labOrderId, null, response);
        return response;
    }

    @Override
    public List<LabOrderResponse> getLabOrderList() {
        log.debug("Entering getLabOrderList");
        List<LabOrderResponse> response = labOrderMapper.toResponseList(
                labOrderRepository.findAllByOrderByTranIdDesc()
        );
        logResult("LabOrder List retrieved", "getLabOrderList", null, null, response);
        return response;
    }

    @Override
    @Transactional
    public LabOrderResponse updateLabOrderById(Long labOrderId, LabOrderRequest request) {
        log.debug("Entering updateLabOrderById | labOrderId : {}", labOrderId);
        LabOrder labOrder = labOrderRepository.getEntityById(labOrderId);
        labOrderMapper.updateEntityFromRequest(request, labOrder);
        LabOrderResponse response = labOrderMapper.toResponse(
                labOrderRepository.save(labOrder)
        );
        logResult("LabOrder updated", "updateLabOrderById", labOrderId, request, response);
        return response;
    }

    @Override
    @Transactional
    public void deleteLabOrderById(Long labOrderId) {
        log.debug("Entering deleteLabOrderById | labOrderId : {}", labOrderId);
        LabOrder labOrder = labOrderRepository.getEntityById(labOrderId);
        labOrderRepository.delete(labOrder);
        logResult("Lab Order deleted", "deleteLabOrderById", labOrderId, null, null);
    }

    @Override
    @Transactional
    public LabOrderResponse patchLabOrderById(Long labOrderId, LabOrderPatchRequest request) {
        log.debug("Entering patchLabOrderById | labOrderId : {}", labOrderId);
        LabOrder labOrder = labOrderRepository.getEntityById(labOrderId);

        labOrderMapper.patch(request, labOrder);
        LabOrderResponse response = labOrderMapper.toResponse(
                labOrderRepository.save(labOrder)
        );
        logResult("Lab Order Partially updated", "patchLabOrderById", labOrderId, request, response);
        return response;
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    void logResult(String action, String methodName, Long labOrderId, Object input, Object result) {
        String logString = "Lab Order Hdr: " + action + " | " + methodName;

        if (labOrderId != null)
            logString += " | labOrderId: " + labOrderId;

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
