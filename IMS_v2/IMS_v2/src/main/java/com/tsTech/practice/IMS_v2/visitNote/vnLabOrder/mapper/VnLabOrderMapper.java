package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request.VnLabOrderRequest;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity.VnLabOrder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = BaseRecordMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface VnLabOrderMapper extends GenericMapper<VnLabOrder, VnLabOrderRequest> {

}
