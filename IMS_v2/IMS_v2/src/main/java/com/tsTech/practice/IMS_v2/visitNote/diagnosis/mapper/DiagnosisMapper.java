package com.tsTech.practice.IMS_v2.visitNote.diagnosis.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.projection.DiagnosisProjection;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisCreateRequest;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.response.DiagnosisIcdMapResponse;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.response.DiagnosisResponse;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.entity.Diagnosis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

/////////////////////////////////////////////
//
// Name: Diagnosis Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
/////////////////////////////////////////////

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BaseRecordMapper.class)
public interface DiagnosisMapper extends GenericMapper<Diagnosis, DiagnosisCreateRequest> {

    @Mapping(target = "baseRecord", source = "diagnosis")
    @Mapping(target = "pnId", source = "diagnosis.visitNote.tranId")
//    @Mapping(target = "patientId", source = "diagnosis.visitNote.patient.tranId")
    @Mapping(target = "icdItemList", source = "updatedIcdMap")
    DiagnosisResponse toResponse(Diagnosis diagnosis, List<DiagnosisIcdMapResponse> updatedIcdMap);

    @Mapping(target = "baseRecord", source = "projection")
    @Mapping(target = "icdItemList", source = "icdItemResponses")
    DiagnosisResponse fromProjectionToResponse(DiagnosisProjection projection, List<DiagnosisIcdMapResponse> icdItemResponses);
}
