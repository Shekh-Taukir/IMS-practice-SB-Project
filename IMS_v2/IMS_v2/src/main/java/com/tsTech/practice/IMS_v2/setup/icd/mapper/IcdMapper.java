package com.tsTech.practice.IMS_v2.setup.icd.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.setup.icd.dto.request.IcdRequest;
import com.tsTech.practice.IMS_v2.setup.icd.dto.response.IcdResponse;
import com.tsTech.practice.IMS_v2.setup.icd.dto.response.IcdSearchResponse;
import com.tsTech.practice.IMS_v2.setup.icd.entities.ICD;
import org.mapstruct.*;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Icd Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 17, 2026 || TaukirS (ER 1014 - icd entity setup coding)
// v1.2 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)

/// //////////////////////////////////////////

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BaseRecordMapper.class)
public interface IcdMapper extends GenericMapper<ICD, IcdRequest> {

    @Mapping(target = "baseRecord", source = "icd")
    IcdResponse toResponse(ICD icd);

    @Mapping(target = "baseRecord", source = "icd")
    List<IcdResponse> toResponseList(List<ICD> icd);

    //here qualifiedByName: will call the method on setting value to target entity, so that icd code is always saved in upper case
    @Override
    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCaseAndTrim")
    ICD fromRequestToEntity(IcdRequest request);

    @Override
    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCaseAndTrim")
    void updateEntityFromRequest(IcdRequest request, @MappingTarget ICD icd);

    //Aug 21, 2026 TaukirS (ER 1016 - diagnosis entity coding)
    List<IcdSearchResponse> toSearchResponseList(List<ICD> icdList);

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    @Named("toUpperCaseAndTrim")
    default String toUpperCaseAndTrim(String value) {
        return value != null ?
                value
                        .trim()
                        .toUpperCase() :
                null;
    }
}
