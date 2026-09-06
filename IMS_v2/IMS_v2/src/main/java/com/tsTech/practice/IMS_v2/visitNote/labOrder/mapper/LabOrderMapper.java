package com.tsTech.practice.IMS_v2.visitNote.labOrder.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.request.LabOrderPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.request.LabOrderRequest;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.response.LabOrderResponse;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.entity.LabOrder;
import org.mapstruct.*;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: LabOrder Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 06, 2026 || TaukirS (ER 1019 - lab order entity coding)
/// //////////////////////////////////////////

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = BaseRecordMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LabOrderMapper extends GenericMapper<LabOrder, LabOrderRequest> {

    @Mapping(target= "baseRecord", source= "entity")
    LabOrderResponse toResponse(LabOrder entity);

    List<LabOrderResponse> toResponseList(List<LabOrder> entity);

    void patch(LabOrderPatchRequest request, @MappingTarget LabOrder entity);
}
