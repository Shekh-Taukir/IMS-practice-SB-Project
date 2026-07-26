package com.tsTech.practice.IMS_v2.common.mapper.baseMapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

////////////////////////////////////////////////
//
// Name: Generic Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 01, 2026 || TaukirS (ER 1006 - mapStruct setup changes)
// v1.1 || type : Change || Jul 25, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

public interface GenericMapper<E, R> {
    //Jul 25, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    // NOTE: moved from usage of DTO, to request / response DTO flow so commenting this logic, of class DTO, to Request DTO
    /*
    D toDto(E entity);
    E toEntity (D dto);
    void updateEntityFromDto(D dto, @MappingTarget E entity );
    */
    E fromRequestToEntity(R request);
    void updateEntityFromRequest(R request, @MappingTarget E entity);
}
