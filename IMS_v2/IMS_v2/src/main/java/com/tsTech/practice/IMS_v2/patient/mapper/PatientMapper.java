package com.tsTech.practice.IMS_v2.patient.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.patient.dtos.records.request.PatientRequest;
import com.tsTech.practice.IMS_v2.patient.dtos.records.response.PatientResponse;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

////////////////////////////////////////////////
//
// Name: Patient Mapper
//
// Description:
//
// Version history:
//
 // v1.1 || type : Change || Jul 01, 2026 || TaukirS (ER 1006 - mapStruct setup changes)
 // v1.1 || type : Change || Jul 25, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

@Mapper(componentModel = "spring", uses = BaseRecordMapper.class)
public interface PatientMapper extends GenericMapper<Patient, PatientRequest> {
    @Mapping(target = "baseRecord", source = "patient")
    PatientResponse fromEntityToResponse(Patient patient);
}
