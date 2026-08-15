package com.tsTech.practice.IMS_v2.setup.service;

import com.tsTech.practice.IMS_v2.setup.dto.request.VisitTypeRequest;
import com.tsTech.practice.IMS_v2.setup.dto.response.VisitTypeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/////////////////////////////////////////////
//
// Name: Visit Type Service
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 15, 2026 || TaukirS (ER 1012 - visit type entity code)
/////////////////////////////////////////////

public interface VisitTypeService {
    VisitTypeResponse createVisitType(VisitTypeRequest request);

    VisitTypeResponse getVisitTypeById(Long visitTypeId);

    List<VisitTypeResponse> getVisitTypeList();

    VisitTypeResponse updateVisitType(Long visitTypeId, VisitTypeRequest request);

    void deleteVisitType(Long visitTypeId);

    VisitTypeResponse patchUpdateVisitType(Long visitTypeId, Map<String, Object> patchData);
}
