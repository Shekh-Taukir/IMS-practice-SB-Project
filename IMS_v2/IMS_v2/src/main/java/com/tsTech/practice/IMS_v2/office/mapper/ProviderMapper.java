package com.tsTech.practice.IMS_v2.office.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.office.dtos.request.ProviderRequest;
import com.tsTech.practice.IMS_v2.office.dtos.response.ProviderResponse;
import com.tsTech.practice.IMS_v2.office.entities.Provider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
        componentModel = "spring",
        uses = BaseRecordMapper.class
)
public interface ProviderMapper extends GenericMapper<Provider, ProviderRequest> {
    @Mapping(target = "baseRecord", source="provider")
    @Mapping(target = "officeId", source="office.tranId")
    ProviderResponse fromEntityToResponse(Provider provider);

    @Override
    @Mapping(target="office", ignore = true)
    void updateEntityFromRequest(ProviderRequest request, @MappingTarget Provider provider);
}
