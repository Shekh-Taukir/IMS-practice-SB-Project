package com.tsTech.practice.IMS_v2.office.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.office.dtos.request.OfficeRequest;
import com.tsTech.practice.IMS_v2.office.dtos.response.OfficeResponse;
import com.tsTech.practice.IMS_v2.office.entities.Office;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

////////////////////////////////////////////////
//
// Name: Office Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

@Mapper(
        uses = BaseRecordMapper.class,
        componentModel = "spring"
)
public interface OfficeMapper extends GenericMapper<Office, OfficeRequest> {

    @Mapping(target = "baseRecord", source = "office")
    OfficeResponse fromEntityToResponse(Office office);
}
