package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.projection.VnLabOrderIcdMapProjection;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.response.VnLabOrderIcdMapResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity.VnLabOrder;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity.VnLabOrderIcdMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Vn Lab Order Icd Map Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 11, 2026 || TaukirS (ER 1022 - vn lab order icd map entity coding)

/// //////////////////////////////////////////

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = BaseRecordMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface VnLabOrderIcdMapMapper extends GenericMapper<VnLabOrder, VnLabOrderRequest> {

    List<VnLabOrderIcdMapResponse> toResponseList(List<VnLabOrderIcdMap> icdMapList);

    @Mapping(target = "icdId", source = "icd.tranId")
    @Mapping(target = "vnLabOrderId", source = "vnLabOrder.tranId")
    VnLabOrderIcdMapResponse toResponse(VnLabOrderIcdMap icdMap);

    List<VnLabOrderIcdMapResponse> fromProjectionToResponseList(List<VnLabOrderIcdMapProjection> projection);
}
