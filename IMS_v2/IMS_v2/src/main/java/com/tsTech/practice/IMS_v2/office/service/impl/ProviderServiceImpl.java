package com.tsTech.practice.IMS_v2.office.service.impl;

import com.tsTech.practice.IMS_v2.office.dto.request.ProviderRequest;
import com.tsTech.practice.IMS_v2.office.dto.response.ProviderResponse;
import com.tsTech.practice.IMS_v2.office.entities.Office;
import com.tsTech.practice.IMS_v2.office.entities.Provider;
import com.tsTech.practice.IMS_v2.office.mapper.ProviderMapper;
import com.tsTech.practice.IMS_v2.office.repository.OfficeRepository;
import com.tsTech.practice.IMS_v2.office.repository.ProviderRepository;
import com.tsTech.practice.IMS_v2.office.service.ProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

////////////////////////////////////////////////
//
// Name: Provider Service IMPL
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final OfficeRepository officeRepository;
    private final ProviderMapper providerMapper;

    @Override
    public List<ProviderResponse> getAllProvider() {
        log.debug("Entering getAllProvider()");
        List<ProviderResponse> providerResponseList = providerRepository.findAll()
                .stream()
                .map(providerMapper::fromEntityToResponse)
                .toList();

        logResult("List Retrieved", "getAllProvider", null, null, providerResponseList);
        return providerResponseList;
    }

    @Override
    public ProviderResponse getProviderById(Long providerId) {
        log.debug("Entering getProviderById() for providerId: {}", providerId);
        Provider provider = providerRepository.getEntityById(providerId);

        ProviderResponse providerResponse = providerMapper.fromEntityToResponse(provider);

        logResult("Retrieved Provider", "getProviderById", providerId, null, providerResponse);
        return providerResponse;
    }

    @Override
    public ProviderResponse createProviderById(ProviderRequest providerRequest) {
        log.debug("Entering createProviderById()");
        Provider provider = providerMapper.fromRequestToEntity(providerRequest);
        Office office = officeRepository.getEntityById(providerRequest.officeId());

        provider.setOffice(office);
        ProviderResponse providerResponse = providerMapper.fromEntityToResponse(providerRepository.save(provider));

        logResult("Created Provider", "createProviderById", null, providerRequest, providerResponse);
        return providerResponse;
    }

    @Override
    public ProviderResponse updateProviderById(Long providerId, ProviderRequest providerRequest) {
        log.debug("Entering updateProviderById() for providerId: {}", providerId);
        Provider provider = providerRepository.getEntityById(providerId);

        providerMapper.updateEntityFromRequest(providerRequest, provider);
        if (!provider.getOffice().getTranId().equals(providerRequest.officeId())) {
            Office office = officeRepository.getEntityById(providerRequest.officeId());
            provider.setOffice(office);
        }
        ProviderResponse providerResponse = providerMapper.fromEntityToResponse(providerRepository.save(provider));

        logResult("Updated Provider", "updateProviderById", providerId, providerRequest, providerResponse);
        return providerResponse;
    }

    @Override
    public Boolean deleteProviderById(Long providerId) {
        log.debug("Entering deleteProviderById() for providerId: {}", providerId);

        Provider provider = providerRepository.getEntityById(providerId);
        providerRepository.delete(provider);

        logResult("Deleted Provider", "deleteProviderById", providerId, null, null);
        return true;
    }

    @Override
    public ProviderResponse patchProviderById(Long providerId, Map<String, Object> patchData) {
        log.debug("Entering patchProviderById() for providerId: {}", providerId);
        Provider provider = providerRepository.getEntityById(providerId);

        patchData.forEach((key, value) -> {
            Field field = ReflectionUtils.getRequiredField(Provider.class, key);
            field.setAccessible(true);

            if(field.getType().isEnum()){
                Class<Enum> enumType = (Class<Enum>) field.getType();
                Enum enumValue = Enum.valueOf(enumType, value.toString());
                value = enumValue;
            }
            ReflectionUtils.setField(field, provider, value);
        });
        ProviderResponse providerResponse = providerMapper.fromEntityToResponse(providerRepository.save(provider));

        logResult("Partially Updated Provider", "patchProviderById", providerId, patchData, providerResponse);
        return providerResponse;
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    void logResult(String action, String methodName, Long providerId, Object userData, Object resultData){
        String logString = "Provider Master | "+action+" | func: "+methodName+"()";

        if(providerId!=null)
            logString+=" | providerId: "+providerId;

        log.debug(logString);

        if(log.isTraceEnabled()){
            if (userData!=null)
                logString+="\n userData: "+userData;

            if (resultData!=null)
                logString+="\n resultData: "+resultData;

            log.trace(logString);
        }

    }
}
