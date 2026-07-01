package com.tsTech.practice.IMS_v2.patient.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.common.mapper.config.UpdateConfig;
import com.tsTech.practice.IMS_v2.patient.dtos.PatientInsuranceDTO;
import com.tsTech.practice.IMS_v2.patient.entities.PatientInsurance;
import org.mapstruct.*;

/////////////////////////////////////////////
//
// Name: Insurance Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 01, 2026 || TaukirS (ER 1006 - mapStruct setup changes)
////////////////////////////////////////////////

@Mapper(
        componentModel = "spring"
        ,config = UpdateConfig.class
)
public interface InsuranceMapper extends GenericMapper<PatientInsurance, PatientInsuranceDTO> {

    /// Mapper function, to update entity obj, from dto obj for update API.
    // Should ignore this PK, FK while updating the insurance entity, so that it doesn't get updated while updating the insurance entity.
//    @Override
    @InheritConfiguration(name = "updateTemplate")
    @Mapping(target = "patient", ignore = true)
    void updateEntityFromDto(PatientInsuranceDTO dto, @MappingTarget PatientInsurance insurance);

    /// Mapper function, to create Insurance entity obj, from dto obj for create API.
    @Mapping(target = "patient", ignore = true)
    PatientInsurance toEntity(PatientInsuranceDTO dto);

    /// Mapper function, to create Insurance DTO obj, from entity obj for List API.
    @Mapping(target = "patientId", source = "patient.tranId")
    PatientInsuranceDTO toDto(PatientInsurance insurance);
}
