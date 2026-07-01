package com.tsTech.practice.IMS_v2.patient.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.patient.dtos.PatientDTO;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

////////////////////////////////////////////////
//
// Name: Patient Mapper
//
// Description:
//
// Version history:
//
 // v1.1 || type : Change || Jul 01, 2026 || TaukirS (ER 1006 - mapStruct setup changes)
////////////////////////////////////////////////

@Mapper(componentModel = "spring")
public interface PatientMapper extends GenericMapper<Patient, PatientDTO> {
}
