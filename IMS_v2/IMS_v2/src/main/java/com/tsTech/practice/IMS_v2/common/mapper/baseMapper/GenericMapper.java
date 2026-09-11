package com.tsTech.practice.IMS_v2.common.mapper.baseMapper;

import org.mapstruct.Condition;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;
import java.time.LocalDateTime;

/// /////////////////////////////////////////////
//
// Name: Generic Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 01, 2026 || TaukirS (ER 1006 - mapStruct setup changes)
// v1.2 || type : Change || Jul 25, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
// v1.3 || type : Change || Sep 04, 2026 || TaukirS (ER 1017 - careplan mst entity coding)
// v1.4 || type : Change || Sep 11, 2026 || TaukirS (ER 1020 - vn lab order entity coding)

/// /////////////////////////////////////////////

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

    //Start Sep 04, 2026 TaukirS (ER 1017 - careplan mst entity coding)
    @Condition
    default boolean isPresent(JsonNullable<?> value) {
        return value != null && value.isPresent();
    }

    default String unwrapString(JsonNullable<String> value) {
        return value.orElse(null);
    }

    default Long unwrapLong(JsonNullable<Long> value) {
        return value.orElse(null);
    }

    default Boolean unwrapBoolean(JsonNullable<Boolean> value) {
        return value.orElse(null);
    }

    default LocalDate unwrapLocalDate(JsonNullable<LocalDate> value) {
        return value.orElse(null);
    }
    //End Sep 04, 2026 TaukirS (ER 1017 - careplan mst entity coding)

    //Start Sep 11, 2026 TaukirS (ER 1020 - vn lab order entity coding)
    default LocalDateTime unwrapLocalDateTime(JsonNullable<LocalDateTime> value) {
        return value.orElse(null);
    }
    //End Sep 11, 2026 TaukirS (ER 1020 - vn lab order entity coding)

}
