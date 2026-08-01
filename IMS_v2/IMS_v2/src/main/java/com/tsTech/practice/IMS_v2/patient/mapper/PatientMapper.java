package com.tsTech.practice.IMS_v2.patient.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.patient.dtos.projectionInterface.PatientProjection;
import com.tsTech.practice.IMS_v2.patient.dtos.records.request.PatientRequest;
import com.tsTech.practice.IMS_v2.patient.dtos.records.response.PatientResponse;
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
 // v1.2 || type : Change || Jul 25, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
 // v1.3 || type : Change || Jul 30, 2026 || TaukirS (ER 1011 - flyway integration & add office and provider in patient)
////////////////////////////////////////////////

@Mapper(componentModel = "spring", uses = BaseRecordMapper.class)
public interface PatientMapper extends GenericMapper<Patient, PatientRequest> {
    @Mapping(target = "baseRecord", source = "patient")
    //Start Aug 01, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
    @Mapping(target = "officeName", source = "patient.office.officeName")
    @Mapping(target = "providerName", source = "patient.provider.firstName")
    @Mapping(target = "officeId", source = "patient.office.tranId")
    @Mapping(target = "providerId", source = "patient.provider.tranId")
    //End Aug 01, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
    PatientResponse fromEntityToResponse(Patient patient);

    //Start Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
    @Mapping(target = "baseRecord", source = "patientProjection")
    PatientResponse fromProjectionToResponse(PatientProjection patientProjection);

    @Mapping(target = "office", ignore = true)
    @Mapping(target = "provider", ignore = true)
    void updateEntityFromRequest(PatientRequest request, @MappingTarget Patient patient);
    //End Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
}
