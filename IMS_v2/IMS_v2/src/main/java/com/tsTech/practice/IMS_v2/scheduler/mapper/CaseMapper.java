package com.tsTech.practice.IMS_v2.scheduler.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.scheduler.dto.projection.CaseProjection;
import com.tsTech.practice.IMS_v2.scheduler.dto.request.CaseRequest;
import com.tsTech.practice.IMS_v2.scheduler.dto.response.CaseResponse;
import com.tsTech.practice.IMS_v2.scheduler.entities.Case;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

/////////////////////////////////////////////
//
// Name: Case Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 16, 2026 || TaukirS (ER 1013 - case master coding)
/////////////////////////////////////////////

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BaseRecordMapper.class)
public interface CaseMapper extends GenericMapper<Case, CaseRequest> {

    @Override
    @Mapping(target = "office", ignore = true)
    Case fromRequestToEntity(CaseRequest request);

    @Mapping(target = "baseRecord", source = "caseEntity")
    @Mapping(target = "patientId", source = "caseEntity.patient.tranId")
    @Mapping(target = "patientName", source = "caseEntity.patient.lastName")
    @Mapping(target = "officeId", source = "caseEntity.patient.office.tranId")
    CaseResponse toResponse(Case caseEntity);

    @Mapping(target = "baseRecord", source = "projection")
    CaseResponse fromProjectionToResponse(CaseProjection projection);

    @Mapping(target = "baseRecord", source = "projection")
    List<CaseResponse> fromProjectionToResponse(List<CaseProjection> projection);
}
