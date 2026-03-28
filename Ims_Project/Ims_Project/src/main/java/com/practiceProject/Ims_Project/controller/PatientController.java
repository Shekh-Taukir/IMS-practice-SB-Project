package com.practiceProject.Ims_Project.controller;

import com.practiceProject.Ims_Project.advices.PageResponse;
import com.practiceProject.Ims_Project.dto.PatientBloodGroupCountDto;
import com.practiceProject.Ims_Project.dto.PatientDto;
import com.practiceProject.Ims_Project.entity.Patient;
import com.practiceProject.Ims_Project.service.PatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Tag(name = "Patient Master", description = "Patient Master Api's")
@RestController
@RequestMapping("/patient")
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/test")
    public String patientTestApi(){
        return "Hello from patient controller";
    }

    @GetMapping("/list")
    public ResponseEntity<List<PatientDto>> getPatientList(){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientsList());
    }

    @GetMapping("/pageList")
    public ResponseEntity<List<PatientDto>> getPagedPatientList(@RequestParam(defaultValue = "tranId") String sortBy,@RequestParam(defaultValue = "asc") String dir, @RequestParam(defaultValue = "0") Integer page){

        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientsListPaged(sortBy, dir, page));
    }

    @GetMapping("/fullPageList")
    public ResponseEntity<Page<Patient>> getFullPagedPatientList(@RequestParam(defaultValue = "tranId") String sortBy, @RequestParam(defaultValue = "asc") String dir, @RequestParam(defaultValue = "0") Integer page){

        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientsFullPagedList(sortBy, dir, page));

    }

    @GetMapping("/customPageList")
    public ResponseEntity<PageResponse<PatientDto>> getCustomPagedPatientList(@RequestParam(defaultValue = "tranId") String sortBy, @RequestParam(defaultValue = "asc") String dir, @RequestParam(defaultValue = "0") Integer page){

        return ResponseEntity.status(HttpStatus.OK).body(patientService.getCustomPatientPage(sortBy, dir, page));

    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable("id") Long patientId){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientById(patientId));
//        Optional<PatientDto> patientDto = patientService.getPatientById(patientId);
//
//        return patientDto
//                .map(patientDto1 -> ResponseEntity.status(HttpStatus.OK).body(patientDto1))
//                .orElseThrow(()->new ResourceNotFoundException("Patient not found for id : "+patientId));
    }

    @GetMapping("/byLastName/{lName}")
    public ResponseEntity<PageResponse<PatientDto>> findPatientByFirstName(@RequestParam(defaultValue = "lastName") String sortBy, @RequestParam(defaultValue = "asc") String dir, @RequestParam(defaultValue = "0") Integer page, @PathVariable("lName") String lastName){

        return ResponseEntity.status(HttpStatus.OK).body(patientService.findPatientByFirstName(sortBy, dir, page,lastName));
    }

    @GetMapping("/bloodGroupStats")
    public ResponseEntity<List<PatientBloodGroupCountDto>> getBloodGroupStatsList(){
        return ResponseEntity.ok(patientService.getBloodGroupStatsList());
    }

    @PatchMapping("/updateLName/{id}")
    public ResponseEntity<PatientDto> updatePatientNameById(@PathVariable("id")Long id, @RequestBody Map<String, String> updates){
        return ResponseEntity.ok(patientService.updatePatientNameById(id, updates));
    }

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(patientDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable("id") Long patientId, @Valid @RequestBody PatientDto patientDto){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.updatePatient(patientId, patientDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePatient(@PathVariable("id") Long patientId){
        return ResponseEntity.status(HttpStatus.OK).body(patientService. deletePatientById(patientId));
    }

    @PatchMapping("{id}")
    public ResponseEntity<PatientDto> updatePartialPatient(@PathVariable("id") Long patientId, @RequestBody Map<String, Object> updates){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.updatePartialPatient(patientId,updates));
    }
}
