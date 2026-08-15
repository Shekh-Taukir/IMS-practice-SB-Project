package com.tsTech.practice.IMS_v2.office.controller;

import com.tsTech.practice.IMS_v2.office.dto.request.OfficeRequest;
import com.tsTech.practice.IMS_v2.office.dto.response.OfficeResponse;
import com.tsTech.practice.IMS_v2.office.service.OfficeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


////////////////////////////////////////////////
//
// Name: Office Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

@RestController
@RequestMapping("/v1/office")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;
    private final String idUrl="/{id}";

    @GetMapping("")
    public ResponseEntity<List<OfficeResponse>> getAllOffices(){
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    @GetMapping(idUrl)
    public ResponseEntity<OfficeResponse> getOfficeById(@PathVariable("id") Long officeId){
        return ResponseEntity.ok(officeService.getOfficeById(officeId));
    }

    @PostMapping("")
    public ResponseEntity<OfficeResponse> createOfficeById(@Valid @RequestBody OfficeRequest officeRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(officeService.createOfficeById(officeRequest));
    }

    @PutMapping(idUrl)
    public ResponseEntity<OfficeResponse> updateOfficeById(@PathVariable("id") Long officeId, @Valid @RequestBody OfficeRequest officeRequest){
        return ResponseEntity.ok(officeService.updateOfficeById(officeId, officeRequest));
    }

    @DeleteMapping(idUrl)
    public ResponseEntity<Boolean> deleteOfficeById(@PathVariable("id") Long officeId){
        return ResponseEntity.ok(officeService.deleteOfficeById(officeId));
    }

    @PatchMapping(idUrl)
    public ResponseEntity<OfficeResponse> patchOfficeById(@PathVariable("id") Long officeId, @RequestBody Map<String, Object> patchData){
        return ResponseEntity.ok(officeService.patchOfficeById(officeId, patchData));
    }
}
