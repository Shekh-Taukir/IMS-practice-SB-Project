package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.response.VnCareplanResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.entity.VnCareplan;
import org.mapstruct.*;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: VisitNote Careplan Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1018 - visit careplan entity coding)

/// //////////////////////////////////////////

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = BaseRecordMapper.class
)
public interface VnCareplanMapper extends GenericMapper<VnCareplan, VnCareplanRequest> {

    @Mapping(target = "baseRecord", source = "entity")
    @Mapping(target = "pnId", source = "entity.visitNote.tranId")
    @Mapping(target = "careplanId", source = "entity.careplan.tranId")
    VnCareplanResponse toResponse(VnCareplan entity);

    List<VnCareplanResponse> toResponseList(List<VnCareplan> entity);

    void patch(VnCareplanPatchRequest request, @MappingTarget VnCareplan entity);
}
