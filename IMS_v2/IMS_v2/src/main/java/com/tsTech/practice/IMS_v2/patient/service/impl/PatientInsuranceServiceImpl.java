package com.tsTech.practice.IMS_v2.patient.service.impl;

import com.tsTech.practice.IMS_v2.common.exception.DuplicateResourceException;
import com.tsTech.practice.IMS_v2.patient.dtos.records.NextPriorityRecord;
import com.tsTech.practice.IMS_v2.patient.dtos.records.request.PatientInsuranceRequest;
import com.tsTech.practice.IMS_v2.patient.dtos.records.response.PatientInsuranceResponse;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import com.tsTech.practice.IMS_v2.patient.entities.PatientInsurance;
import com.tsTech.practice.IMS_v2.patient.enums.InsurancePriority;
import com.tsTech.practice.IMS_v2.patient.mapper.InsuranceMapper;
import com.tsTech.practice.IMS_v2.patient.repository.PatientInsuranceRepository;
import com.tsTech.practice.IMS_v2.patient.repository.PatientRepository;
import com.tsTech.practice.IMS_v2.patient.service.PatientInsuranceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

////////////////////////////////////////////////
//
// Name: Patient Insurance Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 01, 2026 || TaukirS (ER 1005 - patient insurance setup)
// v1.2 || type : Change || Jul 01, 2026 || TaukirS (ER 1006 - mapStruct setup changes)
// v1.3 || type : Change || Jul 23, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
// v1.4 || type : Change || Jul 27, 2026 || TaukirS (ER 1009 - api_error changes for record, func and exception changes)
////////////////////////////////////////////////

@Slf4j      //Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes)
@Service
@RequiredArgsConstructor
public class PatientInsuranceServiceImpl implements PatientInsuranceService {

    private final PatientInsuranceRepository patientInsuranceRepository;
    private final PatientRepository patientRepository;
    private final InsuranceMapper insuranceMapper;  //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)

    @Override
    public List<PatientInsuranceResponse> getAllInsuranceByPatient(Long patientId) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering getAllInsuranceByPatient() for patientId : {}", patientId);

        List<PatientInsuranceResponse> patientInsuranceResponses = patientInsuranceRepository
                .findByPatient_TranId(patientId)
                .stream()
                //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
                .map(insurance -> insuranceMapper.fromEntityToResponse(insurance))
                .toList();

        //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes) - added debug and trace logs
        logResult("Retrieved List", "getAllInsuranceByPatient", null, patientId, null, patientInsuranceResponses);
        return patientInsuranceResponses;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public PatientInsuranceResponse getPatientInsuranceById(Long patientId, Long insId) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering getPatientInsuranceById() | patientId: {} | insId : {}", patientId, insId);

        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        PatientInsuranceResponse patientInsurance =  insuranceMapper.fromEntityToResponse(patientInsuranceRepository.getPatientInsuranceEntityById(patientId, insId));

        //Start Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes) - added debug and trace logs
        logResult("Retrieved by id", "getPatientInsuranceById", insId, patientId, null, patientInsurance);
        return patientInsurance;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public PatientInsuranceResponse addPatientInsuranceById(Long patientId, PatientInsuranceRequest patientInsuranceRequest) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering addPatientInsuranceById() for adding new patient Insurance");

        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        PatientInsurance patientInsurance = insuranceMapper.fromRequestToEntity(patientInsuranceRequest);
        Patient patient = patientRepository.getPatientEntityById(patientId);

        //Jul 27, 2026 TaukirS (ER 1009 - api_error changes for record, func and exception changes) - moved the logic into function of checking duplicate priority exists
        // have to check that incoming priority is not set in any other insurance for that patient, other than OTHER priority
        checkDuplicatePriority(patientId, patientInsuranceRequest.priority());

        patientInsurance.setPatient(patient);

        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        PatientInsuranceResponse newInsurance = insuranceMapper.fromEntityToResponse(patientInsuranceRepository.save(patientInsurance));

        //Start Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes) - added debug and trace logs
        logResult("added new insurance", "addPatientInsuranceById", null, patientId, patientInsuranceRequest, newInsurance);
        return newInsurance;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public Boolean deletePatientInsuranceById(Long patientId, Long insId) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering deletePatientInsuranceById | patientId: {} | insId : {}", patientId, insId);

        PatientInsurance insurance = patientInsuranceRepository.getPatientInsuranceEntityById(patientId, insId);
        patientInsuranceRepository.delete(insurance);

        //Start Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes) - added debug and trace logs
        logResult("Deleted", "deletePatientInsuranceById", insId, patientId, null, null);
        return true;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public PatientInsuranceResponse putPatientInsuranceById(Long patientId, Long insId, PatientInsuranceRequest patientInsuranceRequest) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering putPatientInsuranceById | patientId: {} | insId : {}", patientId, insId);

        PatientInsurance insurance = patientInsuranceRepository.getPatientInsuranceEntityById(patientId, insId);

        //REF:  [Bug Fixed] need to check priority is not overlapping with other insurance before updating the insurance data

        if (insurance.getPriority()!=patientInsuranceRequest.priority())
            checkDuplicatePriority(patientId, patientInsuranceRequest.priority());

        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        // REF:    [bug-fixed] modelMapper is changing the value of insurance.patient.tran_id to insuranceDTO.tranId, and due to that exception occurs,
        //          so have to switch to MapStruct from modelMapper.
        insuranceMapper.updateEntityFromRequest(patientInsuranceRequest, insurance);

        PatientInsuranceResponse updatedInsurance = insuranceMapper.fromEntityToResponse(patientInsuranceRepository.save(insurance));

        //Start Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes) - added debug and trace logs
        logResult("Updated", "putPatientInsuranceById", insId, patientId, patientInsuranceRequest, updatedInsurance);
        return updatedInsurance;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public PatientInsuranceResponse patchPatientInsuranceById(Long patientId, Long insId, Map<String, Object> patchUpdates) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering patchPatientInsuranceById | patientId: {} | insId : {}", patientId, insId);
        PatientInsurance insurance = patientInsuranceRepository.getPatientInsuranceEntityById(patientId, insId);

        //REF:  [Bug Fixed] need to check priority is not overlapping with other insurance before updating the insurance data
        patchUpdates.forEach((key, value) -> {
            Field field = ReflectionUtils.getRequiredField(PatientInsurance.class, key);
            field.setAccessible(true);

            if(field.getType().isEnum()){
                Class<Enum> enumType = (Class<Enum>) field.getType();
                Enum enumValue = Enum.valueOf(enumType, value.toString());

                //Start Jul 27, 2026 TaukirS (ER 1009 - api_error changes for record, func and exception changes)
                if(enumType.equals(InsurancePriority.class))
                    checkDuplicatePriority(patientId, (InsurancePriority) enumValue);
                //End Jul 27, 2026 TaukirS (ER 1009 - api_error changes for record, func and exception changes)
                ReflectionUtils.setField(field, insurance, enumValue);
            }
            else
                ReflectionUtils.setField(field, insurance, value);
        });
        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        PatientInsuranceResponse updatedInsurance = insuranceMapper.fromEntityToResponse(patientInsuranceRepository.save(insurance));

        //Start Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes) - added debug and trace logs
        logResult("Partially updated", "patchPatientInsuranceById", insId, patientId, patchUpdates, updatedInsurance);
        return updatedInsurance;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public NextPriorityRecord getNextPriority(Long patientId){

        // REF: [Bug Fixed] Retrieve for patient:5, next priority showing: Other, and current are secondary, primary, quaternary,
        //  Issues: 1. sequence of current priorities are not proper.
//                      => as in the frontend, the list will be helpful in showing the used priorities in the dropdown, so order doesn't matter here
        //          2. ideally it should show tertiary, but showing other.
//                      => fixed

        //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        String newPriority = InsurancePriority.OTHER.toString();
        log.debug("Entering getNextPriority | patientId: {}", patientId);
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)

        Set<String> currentPriorities = new HashSet<>(patientInsuranceRepository.getCurrentPriorities(patientId));
        if (currentPriorities.isEmpty()) {
            //as there are no insurances added for this patient, so default priority should be primary for new insurance.
            newPriority = InsurancePriority.PRIMARY.toString();
        } else{
            for (InsurancePriority prio : InsurancePriority.values())
                if (!currentPriorities.contains(prio.toString())) {
                    newPriority = prio.toString();
                    break;
                }
        }
        NextPriorityRecord nextPriorityRecord = new NextPriorityRecord(newPriority, currentPriorities);

        //Start Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes) - added debug and trace logs
        logResult("Next priority retrieved","getNextPriority",null, patientId,null, nextPriorityRecord);
        return nextPriorityRecord;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }


    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    private void logResult(String action, String methodName, Long insId, Long patientId, Object userData, Object dtoResult){
        String debugString = "Patient Insurance | " + action + " | " + methodName + "()";

        if (insId != null)
            debugString += " | insId: " + insId;

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

    //Start Jul 27, 2026 TaukirS (ER 1009 - api_error changes for record, func and exception changes)
    private void checkDuplicatePriority(Long patientId, InsurancePriority newPriority){
        //if new insurance request is other, then we don't need to check further for duplicate priority exists or not for that patient.
        if(newPriority == InsurancePriority.OTHER)
            return;

        if (Boolean.TRUE.equals(patientInsuranceRepository.existsByPatient_TranIdAndPriority(patientId, newPriority))) {
            log.debug("Duplicate Entity Exception occurred | Method : addPatientInsuranceById() | Entity: Insurance Priority | patientId: {}", patientId);

            throw new DuplicateResourceException("DUPLICATE_INSURANCE_PRIORITY","Insurance with Priority: "+newPriority+" already exists for the patient : "+patientId);
        }
    }
    //End Jul 27, 2026 TaukirS (ER 1009 - api_error changes for record, func and exception changes)
}
