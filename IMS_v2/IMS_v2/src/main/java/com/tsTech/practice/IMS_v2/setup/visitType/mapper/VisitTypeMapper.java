package com.tsTech.practice.IMS_v2.setup.visitType.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.setup.visitType.dto.request.VisitTypeRequest;
import com.tsTech.practice.IMS_v2.setup.visitType.dto.response.VisitTypeResponse;
import com.tsTech.practice.IMS_v2.setup.visitType.entities.VisitType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Visit Type Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 15, 2026 || TaukirS (ER 1012 - visit type entity code)

/////////////////////////////////////////////

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BaseRecordMapper.class)
public interface VisitTypeMapper extends GenericMapper<VisitType, VisitTypeRequest> {

    @Mapping(target = "baseRecord", source = "visitType")
    VisitTypeResponse toResponse(VisitType visitType);

    List<VisitTypeResponse> toResponseList(List<VisitType> visitTypeList);
}
