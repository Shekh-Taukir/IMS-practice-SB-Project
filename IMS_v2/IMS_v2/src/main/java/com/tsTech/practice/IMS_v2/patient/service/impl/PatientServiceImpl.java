package com.tsTech.practice.IMS_v2.patient.service.impl;

import com.tsTech.practice.IMS_v2.office.entities.Office;
import com.tsTech.practice.IMS_v2.office.entities.Provider;
import com.tsTech.practice.IMS_v2.office.repository.OfficeRepository;
import com.tsTech.practice.IMS_v2.office.repository.ProviderRepository;
import com.tsTech.practice.IMS_v2.patient.dto.records.request.PatientRequest;
import com.tsTech.practice.IMS_v2.patient.dto.records.response.PatientResponse;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import com.tsTech.practice.IMS_v2.patient.mapper.PatientMapper;
import com.tsTech.practice.IMS_v2.patient.repository.PatientRepository;
import com.tsTech.practice.IMS_v2.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

////////////////////////////////////////////////
//
// Name: PatientServiceImpl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
// v1.2 || type : Change || Jun 25, 2026 || TaukirS (ER 1003 - validation and generalize response and error coding)
// v1.3 || type : Change || Jun 29, 2026 || TaukirS (ER 1005 - patient insurance setup)
// v1.4 || type : Change || Jul 01, 2026 || TaukirS (ER 1006 - mapStruct setup changes)
// v1.5 || type : Change || Jul 24, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
// v1.6 || type : Change || Jul 30, 2026 || TaukirS (ER 1011 - flyway integration & add office and provider in patient)
////////////////////////////////////////////////

@Service
@RequiredArgsConstructor
@Slf4j  //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
    private final PatientMapper patientMapper;

    //Start Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
    private final OfficeRepository officeRepository;
    private final ProviderRepository providerRepository;
    //End Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)


    @Override
    public List<PatientResponse> getAllPatients() {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering getAllPatients()");

        List<PatientResponse> patientResponseList = patientRepository
                //Aug 01, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient) - updated to PatientProjection as data retrieve mechanism
                .findAllWithOfficeAndProvider()
                .stream()
                //Aug 01, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
                //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
                .map(patientMapper::fromProjectionToResponse)
                .toList();

        //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        logResult("List Retrieved","getAllPatients",null, null, patientResponseList);
        return patientResponseList;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public PatientResponse getPatientById(Long patientId) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering getPatientById()");

        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        // made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        //Start Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
        PatientResponse patientResponse =  patientMapper.fromProjectionToResponse(
                        patientRepository.getPatientWithOfficeAndProviderEntityById(patientId)
        );
        //End Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)

        //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        logResult("Patient Retrieved","getPatientById", patientId, null, patientResponse);
        return patientResponse;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public PatientResponse addPatient(PatientRequest patientRequest) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering addPatient()");

        Patient patient = patientMapper.fromRequestToEntity(patientRequest);
        //Start Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
        Office office = officeRepository.getEntityById(patientRequest.officeId());
        Provider provider = providerRepository.getEntityByIdAndOffice(patientRequest.providerId(), patientRequest.officeId());

        patient.setOffice(office);
        patient.setProvider(provider);
        //End Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)

        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        PatientResponse newPatient = patientMapper.fromEntityToResponse(patientRepository.save(patient));

        //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        logResult("Added new patient","addPatient",null, patientRequest, newPatient);
        return newPatient;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public PatientResponse updatePatientById(Long patientId, PatientRequest patientRequest) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering updatePatientById()");

        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        /// made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        Patient patient = patientRepository.getPatientEntityById(patientId);
        //Start Jul 31, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
        if(!patient.getOffice().getTranId().equals(patientRequest.officeId())){
            Office office = officeRepository.getEntityById(patientRequest.officeId());
            patient.setOffice(office);
        }

        if(!patient.getProvider().getTranId().equals(patientRequest.providerId())){
            Provider provider = providerRepository.getEntityByIdAndOffice(patientRequest.providerId(), patientRequest.officeId());
            patient.setProvider(provider);
        }
        //End Jul 31, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)

        //Start Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        patientMapper.updateEntityFromRequest(patientRequest, patient);
        PatientResponse updatedPatient =  patientMapper.fromEntityToResponse(patientRepository.save(patient));

        logResult("Patient Updated","updatePatientById",patientId, patientRequest, updatedPatient);
        return updatedPatient;
        //End Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
    }

    @Override
    public Boolean deletePatientById(Long patientId) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering deletePatientById()");

        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        // made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        Patient patient = patientRepository.getPatientEntityById(patientId);
        patientRepository.delete(patient);

        //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        logResult("Patient Deleted","deletePatientById",patientId, null, null);
        return true;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public PatientResponse patchPatientById(Long patientId, Map<String, Object> patchData) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering patchPatientById() for patient: {}", patientId);

        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        /// made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        Patient patient = patientRepository.getPatientEntityById(patientId);

        //Start Jul 31, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
        boolean ibOfficeChange = patchData.containsKey("officeId");
        boolean ibProviderChange = patchData.containsKey("providerId");

        if(ibProviderChange || ibOfficeChange){
            Long officeId = patchData.containsKey("officeId") ?
                    Long.valueOf(patchData.get("officeId").toString()) :
                    patient.getOffice().getTranId();

            Long providerId = patchData.containsKey("providerId") ?
                    Long.valueOf(patchData.get("providerId").toString()) :
                    patient.getProvider().getTranId();

            if(ibOfficeChange){
                if(!officeId.equals(patient.getOffice().getTranId())){
                    log.debug("Updating office for patient id: {} | officeId: {} | function : patchPatientById", patientId, officeId);

                    providerRepository.checkProviderFallsUnderOffice(providerId, officeId);

                    Office office = officeRepository.getEntityById(officeId);
                    patient.setOffice(office);

                    log.debug("Office updated in patient: {} | function : patchPatientById", patientId);
                    log.trace("Office updated in patient id: {} | office : {} | function : patchPatientById", patientId, office);
                }
            }

            if (ibProviderChange) {
                if (!providerId.equals(patient.getProvider().getTranId())){
                    log.debug("Updating provider for patient id: {} | providerId: {} | function : patchPatientById", patientId, providerId);

                    Provider provider = providerRepository.getEntityByIdAndOffice(providerId, officeId);
                    patient.setProvider(provider);

                    log.debug("Provider updated in patient: {} | function : patchPatientById", patientId);
                    log.trace("Provider updated in patient id: {} | provider : {} | function : patchPatientById", patientId, provider);
                }
            }
        }
        //End Jul 31, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)

        patchData.forEach((key, value)->{
            if (key.equals("officeId") || key.equals("providerId")) {
                //Aug 01, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient) - As office and provider calculation is done prior, so no need to calculate in the for each loop.
                return;
            } else {
                Field fieldToBeUpdated = ReflectionUtils.getRequiredField(Patient.class, key);
                fieldToBeUpdated.setAccessible(true);

                if (fieldToBeUpdated.getType().isEnum()) {
                    Class<Enum> enumType = (Class<Enum>) fieldToBeUpdated.getType();
                    value = Enum.valueOf(enumType, value.toString());
                }
                ReflectionUtils.setField(fieldToBeUpdated, patient, value);
            }
        });

        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        PatientResponse updatedPatient = patientMapper.fromEntityToResponse(patientRepository.save(patient));

        //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        logResult("Patient partially updated","patchPatientById", patientId, patchData, updatedPatient);
        return updatedPatient;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    private void logResult(String action, String methodName, Long patientId, Object userData, Object dtoResult){
        String debugString = "Patient Mst | " + action + " | " + methodName + "()";

        if(patientId != null)
            debugString+= " | patientId: " + patientId;

        log.debug(debugString);

        if(log.isTraceEnabled()) {
            if (userData != null)
                debugString += " \n userData: " + userData;

            if (dtoResult != null)
                debugString += " \n result: " + dtoResult;
            log.trace(debugString);
        }
    }
    //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
}
