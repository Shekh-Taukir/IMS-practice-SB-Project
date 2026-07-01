package com.tsTech.practice.IMS_v2.common.mapper.baseMapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

////////////////////////////////////////////////
//
// Name: Generic Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 01, 2026 || TaukirS (ER 1006 - mapStruct setup changes)
////////////////////////////////////////////////

public interface GenericMapper<E, D> {
    D toDto(E entity);
    E toEntity (D dto);
    void updateEntityFromDto(D dto, @MappingTarget E entity );
}
