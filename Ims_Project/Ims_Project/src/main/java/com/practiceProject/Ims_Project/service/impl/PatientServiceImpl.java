package com.practiceProject.Ims_Project.service.impl;

import com.practiceProject.Ims_Project.advices.PageResponse;
import com.practiceProject.Ims_Project.dto.PatientBloodGroupCountDto;
import com.practiceProject.Ims_Project.dto.PatientDto;
import com.practiceProject.Ims_Project.entity.Patient;
import com.practiceProject.Ims_Project.exception.ResourceNotFoundException;
import com.practiceProject.Ims_Project.repository.PatientRepository;
import com.practiceProject.Ims_Project.service.PatientService;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    private final int PAGE_SIZE = 5;

    @Override
    public List<PatientDto> getPatientsList() {
        List<Patient> patientList = patientRepository.findAll();

        return patientList
                .stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .toList();
    }

    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        Patient newPatient = modelMapper.map(patientDto, Patient.class);
        newPatient = patientRepository.save(newPatient);
        return modelMapper.map(newPatient, PatientDto.class);
    }

    @Override
    public PatientDto getPatientById(Long patientId) {
        isPatientExists(patientId);
        Patient patient = patientRepository.findById(patientId).get();

        return modelMapper.map(patient,PatientDto.class);
//        return patientRepository.findById(patientId).map(patient -> modelMapper.map(patient, PatientDto.class));
    }

    @Override
    public PatientDto updatePatient(Long patientId, PatientDto patientDto) {
        isPatientExists(patientId);
        Patient patient = patientRepository.findById(patientId).get();

        patientDto.setTranId(patientId);
        modelMapper.map(patientDto, patient);
        patient = patientRepository.save(patient);

        return modelMapper.map(patient,PatientDto.class);
    }

    @Override
    public String deletePatientById(Long patientId) {
        isPatientExists(patientId);
        patientRepository.deleteById(patientId);
        return "Patient Deleted | ID : "+patientId;
    }

    @Override
    public PatientDto updatePartialPatient(Long patientId, Map<String, Object> updates) {
        isPatientExists(patientId);

        Patient patient = patientRepository.findById(patientId).get();

        updates.forEach((field, value)->{
            Field fieldToBeUdpated = ReflectionUtils.getRequiredField(Patient.class,field);
            fieldToBeUdpated.setAccessible(true);

            if (fieldToBeUdpated.getType().isEnum()){
                ReflectionUtils.setField(
                        fieldToBeUdpated,
                        patient,
                        Enum.valueOf((Class<Enum>)fieldToBeUdpated.getType(),value.toString()));
            }
            else {
                ReflectionUtils.setField(fieldToBeUdpated, patient, value);
            }
        });

        return modelMapper.map(patientRepository.save(patient),PatientDto.class);
    }

    @Override
    public @Nullable List<PatientDto> getPatientsListPaged(String sortBy, String dir, Integer page) {
        Pageable pageable = PageRequest.of(
                page,
                PAGE_SIZE,
                Sort.by(Sort.Direction.fromString(dir),sortBy));

        return patientRepository.
                findAll(pageable).
                map(patient -> modelMapper.map(patient,PatientDto.class))
                .toList();
    }

    @Override
    public Page<Patient> getPatientsFullPagedList(String sortBy, String dir, Integer page) {

        //instead of creating pageable object per function, created a new function to return pageable object based on requested values
        Pageable pageable = createPagable(page, sortBy, dir);
//        Pageable pageable = PageRequest.of(page,PAGE_SIZE,
//                Sort.by(
//                        new Sort.Order(Sort.Direction.fromString(dir),sortBy),
//                        Sort.Order.desc("tranId")
//        ));
        Page<Patient> patientPage =  patientRepository.findAll(pageable);
        return patientPage;
    }

    @Override
    public PageResponse<PatientDto> getCustomPatientPage(String sortBy, String dir, Integer page) {
        Pageable pageable = createPagable(page,sortBy,dir);

        Page<Patient> patientPage =  patientRepository.findAll(pageable);

//        List<PatientDto> patientDtoList = patientPage
//                .getContent()
//                .stream()
//                .map(patient -> modelMapper.map(patient,PatientDto.class))
//                .toList();

        return new PageResponse<>(
                patientPage.getTotalPages(),
                patientPage
                        .getContent()
                        .stream()
                        .map(patient->modelMapper.map(patient,PatientDto.class))
                        .toList()
                );
    }

    @Override
    public PageResponse<PatientDto> findPatientByFirstName(String sortBy, String dir, Integer page, String lastName) {
        Pageable pageable = createPagable(page, sortBy,dir);

        Page<Patient> patientPage = patientRepository.findByLastNameContainingIgnoreCase(lastName,pageable);

        return new PageResponse<>(
                patientPage.getTotalPages(),
                patientPage
                        .getContent()
                        .stream()
                        .map(patient -> modelMapper.map(patient,PatientDto.class))
                        .toList()
        );
    }

    @Override
    public List<PatientBloodGroupCountDto> getBloodGroupStatsList() {
        return patientRepository.getBloodGroupStatsList();
    }

    @Override
    public PatientDto updatePatientNameById(Long id, String lastName) {
        isPatientExists(id);

        int updatedRow = patientRepository.updateLastNameById(lastName, id);
        return getPatientById(id);
    }


    //----------------------Internal Methods for Service Methods
    private void isPatientExists(Long patientId){
        if (!patientRepository.existsById(patientId))
            throw new ResourceNotFoundException("Patient not found for id : "+patientId);
    }

    private Pageable createPagable(Integer apiPage, String apiSortBy, String apiSortOrder/*, String defSortBy, String defSortOrder*/){
//        if (defSortBy == null)
//            defSortBy = "tranId";
//
//        if (defSortOrder == null)
//            defSortOrder = "desc";

        return PageRequest.of(apiPage,PAGE_SIZE,
                Sort.by(
                        new Sort.Order(Sort.Direction.fromString(apiSortOrder),apiSortBy),
                        new Sort.Order(Sort.Direction.fromString("desc"), "tranId")
                ));
    }
}
