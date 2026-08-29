package com.tsTech.practice.IMS_v2.visitNote.core.service;

import com.tsTech.practice.IMS_v2.visitNote.core.dto.request.VisitNoteRequest;
import com.tsTech.practice.IMS_v2.visitNote.core.dto.response.VisitNoteResponse;

import java.util.List;
import java.util.Map;

public interface VisitNoteService{
    VisitNoteResponse createVisit(VisitNoteRequest request);

    VisitNoteResponse getVisitById(Long patientId, Long pnId);

    List<VisitNoteResponse> getAllVisits(Long patientId);

    VisitNoteResponse updateVisitById(Long patientId, Long pnId, VisitNoteRequest request);

    void deleteVisitById(Long patientId, Long pnId);

    VisitNoteResponse patchUpdateVisitById(Long patientId, Long pnId, Map<String, Object> patchUpdate);
}
