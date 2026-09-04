package com.tsTech.practice.IMS_v2.setup.careplan.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.setup.careplan.dto.request.CareplanPatchRequest;
import com.tsTech.practice.IMS_v2.setup.careplan.dto.request.CareplanRequest;
import com.tsTech.practice.IMS_v2.setup.careplan.dto.response.CareplanResponse;
import com.tsTech.practice.IMS_v2.setup.careplan.entity.Careplan;
import org.mapstruct.*;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

/// ///////////////////////////////////////
//
// Name: Careplan Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1017 - careplan mst entity coding)

/// //////////////////////////////////////////

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = BaseRecordMapper.class
)
public interface CareplanMapper extends GenericMapper<Careplan, CareplanRequest> {

    @Mapping(target = "baseRecord", source = "entity")
    CareplanResponse toResponse(Careplan entity);

    List<CareplanResponse> toResponseList(List<Careplan> entityList);

    void patch(CareplanPatchRequest request, @MappingTarget Careplan entity);
}
