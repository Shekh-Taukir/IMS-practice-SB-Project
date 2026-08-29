package com.tsTech.practice.IMS_v2.visitNote.core.mapper;

import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.BaseRecordMapper;
import com.tsTech.practice.IMS_v2.common.mapper.baseMapper.GenericMapper;
import com.tsTech.practice.IMS_v2.visitNote.core.dto.projection.VisitNoteProjection;
import com.tsTech.practice.IMS_v2.visitNote.core.dto.request.VisitNoteRequest;
import com.tsTech.practice.IMS_v2.visitNote.core.dto.response.VisitNoteResponse;
import com.tsTech.practice.IMS_v2.visitNote.core.entities.VisitNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

/////////////////////////////////////////////
//
// Name: Visit Note Mapper
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 20, 2026 || TaukirS (ER 1015 - visit note entity coding)
/////////////////////////////////////////////

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BaseRecordMapper.class)
public interface VisitNoteMapper extends GenericMapper<VisitNote, VisitNoteRequest> {

    @Mapping(target = "baseRecord", source = "entity")
    @Mapping(target = "patientId", source = "entity.patient.tranId")
    @Mapping(target = "officeId", source = "entity.office.tranId")
    @Mapping(target = "providerId", source = "entity.provider.tranId")
    @Mapping(target = "visitTypeId", source = "entity.visitType.tranId")
    VisitNoteResponse toResponse(VisitNote entity);

    @Mapping(target = "baseRecord", source = "projection")
    VisitNoteResponse fromProjectionToResponse(VisitNoteProjection projection);

    @Mapping(target = "baseRecord", source = "projection")
    List<VisitNoteResponse> fromProjectionToResponseList(List<VisitNoteProjection> projection);


}
