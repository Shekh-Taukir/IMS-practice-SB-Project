package com.tsTech.practice.IMS_v2.office.service.impl;

import com.tsTech.practice.IMS_v2.office.dto.request.OfficeRequest;
import com.tsTech.practice.IMS_v2.office.dto.response.OfficeResponse;
import com.tsTech.practice.IMS_v2.office.entities.Office;
import com.tsTech.practice.IMS_v2.office.mapper.OfficeMapper;
import com.tsTech.practice.IMS_v2.office.repository.OfficeRepository;
import com.tsTech.practice.IMS_v2.office.service.OfficeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

////////////////////////////////////////////////
//
// Name: Office Service IMPL
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
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;
    private final OfficeMapper mapper;

    @Override
    public List<OfficeResponse> getAllOffices() {
        log.debug("Entering getAllOffices()");
        List<OfficeResponse> officeResponseList = officeRepository.findAll()
                .stream()
                .map(mapper::fromEntityToResponse)
                .toList();

        logResult("Retrieved List", "getAllOffices", null, null, officeResponseList);
        return officeResponseList;
    }

    @Override
    public OfficeResponse getOfficeById(Long officeId) {
        log.debug("Entering getOfficeById for officeID: {}", officeId);
        OfficeResponse officeResponse = mapper.fromEntityToResponse(officeRepository.getEntityById(officeId));

        logResult("Retrieved by Id", "getOfficeById",officeId, null, officeResponse);
        return officeResponse;
    }

    @Override
    public OfficeResponse createOfficeById(OfficeRequest officeRequest) {
        log.debug("Entering createOfficeById");
        Office office = mapper.fromRequestToEntity(officeRequest);

        OfficeResponse officeResponse = mapper.fromEntityToResponse(officeRepository.save(office));
        logResult("Office Created", "createOfficeById", null, officeRequest, officeResponse);
        return officeResponse;
    }

    @Override
    public OfficeResponse updateOfficeById(Long officeId, OfficeRequest officeRequest) {
        log.debug("Entering updateOfficeById");
        Office office = officeRepository.getEntityById(officeId);

        mapper.updateEntityFromRequest(officeRequest, office);
        OfficeResponse officeResponse = mapper.fromEntityToResponse(officeRepository.save(office));

        logResult("Office Updated", "updateOfficeById", officeId, officeRequest, officeResponse);
        return officeResponse;
    }

    @Override
    public Boolean deleteOfficeById(Long officeId) {
        log.debug("Entering deleteOfficeById for officeId: {}", officeId);
        Office office = officeRepository.getEntityById(officeId);

        officeRepository.delete(office);
        logResult("Deleting Office", "deleteOfficeById", officeId, null, null);
        return true;
    }

    @Override
    public OfficeResponse patchOfficeById(Long officeId, Map<String, Object> patchData) {
        log.debug("Entering patchOfficeById for officeId: {}", officeId);
        Office office = officeRepository.getEntityById(officeId);

        patchData.forEach((key, value)->{
            Field field = ReflectionUtils.getRequiredField(Office.class, key);
            field.setAccessible(true);

            if(field.getType().isEnum()){
                Class<Enum> enumType = (Class<Enum>) field.getType();
                Enum enumValue = Enum.valueOf(enumType, value.toString());
                value = enumValue;
            }
            ReflectionUtils.setField(field, office, value);
        });

        OfficeResponse officeResponse = mapper.fromEntityToResponse(officeRepository.save(office));
        logResult("Patch Update", "patchOfficeById", officeId, patchData, officeResponse);
        return officeResponse;
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    void logResult(String action, String methodName, Long officeId, Object userData, Object resultData){
        String logString = "Office Master | "+action+" | func: "+methodName+"()";

        if(officeId!=null)
            logString+=" | officeId: "+officeId;

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
