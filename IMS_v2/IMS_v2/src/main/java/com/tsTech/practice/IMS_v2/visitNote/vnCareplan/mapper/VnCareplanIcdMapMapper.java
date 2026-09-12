package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.projection.VnCareplanIcdMapProjection;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request.VnCareplanIcdMapRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.response.VnCareplanIcdMapResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.response.VnCareplanResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.entity.VnCareplan;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.entity.VnCareplanIcdMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Vn Careplan Icd Map Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 08, 2026 || TaukirS (ER 1021 - vn careplan icd map entity coding)
// v1.2 || type : Change || Sep 10, 2026 || TaukirHp (ER 1021 - vn careplan icd map entity coding)
/// //////////////////////////////////////////

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = BaseRecordMapper.class
)
public interface VnCareplanIcdMapMapper extends GenericMapper<VnCareplanIcdMap, VnCareplanIcdMapRequest> {

    @Mapping(target = "vnCareplanId", source = "entity.vnCareplan.tranId")
    @Mapping(target = "icdId", source = "entity.icd.tranId")
    VnCareplanIcdMapResponse toResponse(VnCareplanIcdMap entity);

    List<VnCareplanIcdMapResponse> toResponseList(List<VnCareplanIcdMap> entity);

    //Sep 10, 2026 TaukirHp (ER 1021 - vn careplan icd map entity coding)
    List<VnCareplanIcdMapResponse> fromProjectionToResponse(List<VnCareplanIcdMapProjection> projectionList);
}
