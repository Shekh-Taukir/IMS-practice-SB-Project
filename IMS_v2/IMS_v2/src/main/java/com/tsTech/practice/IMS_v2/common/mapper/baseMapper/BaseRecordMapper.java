package com.tsTech.practice.IMS_v2.common.mapper.baseMapper;

import com.tsTech.practice.IMS_v2.common.dto.projectionInterface.BaseProjection;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;
import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import org.mapstruct.Mapper;

/////////////////////////////////////////////
//
// Name: Base Record Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 24, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
// v1.2 || type : Change || Jul 30, 2026 || TaukirS (ER 1011 - flyway integration & add office and provider in patient)
////////////////////////////////////////////////

@Mapper(componentModel = "spring")
public interface BaseRecordMapper {

    default BaseRecord toBaseRecord(BaseEntity entity){
        return new BaseRecord(entity.getTranId(), entity.getCreatedAt(), entity.getUpdatedAt(), entity.getIsActive());
    }

    //Start Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
    default BaseRecord toBaseRecord(BaseProjection projection){
        return new BaseRecord(projection.getTranId(), projection.getCreatedAt(), projection.getUpdatedAt(), projection.getIsActive());
    }
    //End Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
}
