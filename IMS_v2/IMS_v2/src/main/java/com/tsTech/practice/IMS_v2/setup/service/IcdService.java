package com.tsTech.practice.IMS_v2.setup.service;

import com.tsTech.practice.IMS_v2.setup.dto.request.IcdBulkRequest;
import com.tsTech.practice.IMS_v2.setup.dto.request.IcdRequest;
import com.tsTech.practice.IMS_v2.setup.dto.response.IcdResponse;
import com.tsTech.practice.IMS_v2.setup.dto.response.IcdSearchResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

/////////////////////////////////////////////
//
// Name: Icd Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 17, 2026 || TaukirS (ER 1014 - icd entity setup coding)
/////////////////////////////////////////////

public interface IcdService {
    IcdResponse createIcd(IcdRequest request);

    List<IcdResponse> createIcdByJsonList(IcdBulkRequest request);

    IcdResponse getIcdById(Long icdId);

    List<IcdResponse> getIcdList(String keyword);

    List<IcdSearchResponse> getIcdListBySearch(String keyword);

    IcdResponse updateIcd(Long icdId, IcdRequest request);

    IcdResponse expiryIcdById(Long icdId);

    IcdResponse patchUpdateIcdById(Long icdId, Map<String, Object> patchData);
}
