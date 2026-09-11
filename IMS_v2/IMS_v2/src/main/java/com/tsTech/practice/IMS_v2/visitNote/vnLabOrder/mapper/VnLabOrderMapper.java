package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.response.VnLabOrderResponse;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity.VnLabOrder;
import org.mapstruct.*;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: VisitNote LabOrder Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : New Func || Sep 08, 2026 || TaukirHp (ER 1020 - vn lab order entity coding)
// v1.1 || type : Change || Sep 11, 2026 || TaukirS (ER 1020 - vn lab order entity coding)

/// //////////////////////////////////////////

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = BaseRecordMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface VnLabOrderMapper extends GenericMapper<VnLabOrder, VnLabOrderRequest> {

    @Mapping(target = "baseRecord", source = "entity")
    @Mapping(target = "pnId", source = "entity.visitNote.tranId")
    @Mapping(target = "labOrderId", source = "entity.labOrder.tranId")
    VnLabOrderResponse toResponse(VnLabOrder entity);

    List<VnLabOrderResponse> toResponseList(List<VnLabOrder> entity);

    void patch(VnLabOrderPatchRequest request, @MappingTarget VnLabOrder entity);
}
