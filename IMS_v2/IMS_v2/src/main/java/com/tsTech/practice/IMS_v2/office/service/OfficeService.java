package com.tsTech.practice.IMS_v2.office.service;

import com.tsTech.practice.IMS_v2.office.dtos.request.OfficeRequest;
import com.tsTech.practice.IMS_v2.office.dtos.response.OfficeResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

////////////////////////////////////////////////
//
// Name: Office Service
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

public interface OfficeService {
    List<OfficeResponse> getAllOffices();

    OfficeResponse getOfficeById(Long officeId);

    OfficeResponse createOfficeById(OfficeRequest officeRequest);

    OfficeResponse updateOfficeById(Long officeId, OfficeRequest officeRequest);

    Boolean deleteOfficeById(Long officeId);

    OfficeResponse patchOfficeById(Long officeId, Map<String, Object> patchData);
}
