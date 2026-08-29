package com.tsTech.practice.IMS_v2.visitNote.diagnosis.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.projection.DiagnosisIcdMapProjection;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisIcdMapRequest;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.response.DiagnosisIcdMapResponse;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.entity.DiagnosisIcdMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

/////////////////////////////////////////////
//
// Name: Diagnosis Icd Map Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
/////////////////////////////////////////////

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BaseRecordMapper.class)
public interface DiagnosisIcdMapMapper extends GenericMapper<DiagnosisIcdMap, DiagnosisIcdMapRequest> {

    List<DiagnosisIcdMapResponse> toResponseList(List<DiagnosisIcdMap> icdMapList);

    @Mapping(target = "icdId", source = "icd.tranId")
    DiagnosisIcdMapResponse toResponse(DiagnosisIcdMap icdMap);

    @Mapping(target = "diagnosisIcdMapId", source = "tranId" )
    DiagnosisIcdMapResponse fromProjectionToResponse(DiagnosisIcdMapProjection projection);

    List<DiagnosisIcdMapResponse> fromProjectionToResponseList(List<DiagnosisIcdMapProjection> projection);
}
