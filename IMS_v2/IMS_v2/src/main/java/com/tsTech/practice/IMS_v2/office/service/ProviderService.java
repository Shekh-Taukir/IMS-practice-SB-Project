package com.tsTech.practice.IMS_v2.office.service;

import com.tsTech.practice.IMS_v2.office.dtos.request.ProviderRequest;
import com.tsTech.practice.IMS_v2.office.dtos.response.ProviderResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

////////////////////////////////////////////////
//
// Name: Provider Service
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

public interface ProviderService {
    List<ProviderResponse> getAllProvider();

    ProviderResponse getProviderById(Long providerId);

    ProviderResponse createProviderById(ProviderRequest providerRequest);

    ProviderResponse updateProviderById(Long providerId, ProviderRequest providerRequest);

    Boolean deleteProviderById(Long providerId);

    ProviderResponse patchProviderById(Long providerId, Map<String, Object> patchData);
}
