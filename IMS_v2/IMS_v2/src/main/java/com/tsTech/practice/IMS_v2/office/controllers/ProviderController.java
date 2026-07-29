package com.tsTech.practice.IMS_v2.office.controllers;

import com.tsTech.practice.IMS_v2.office.dtos.request.ProviderRequest;
import com.tsTech.practice.IMS_v2.office.dtos.response.ProviderResponse;
import com.tsTech.practice.IMS_v2.office.service.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

////////////////////////////////////////////////
//
// Name: Provider Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/provider")
public class ProviderController {

    private final ProviderService providerService;
    private final String idUrl="/{id}";

    @GetMapping("")
    public ResponseEntity<List<ProviderResponse>> getAllProvider(){
        return ResponseEntity.ok(providerService.getAllProvider());
    }

    @GetMapping(idUrl)
    public ResponseEntity<ProviderResponse> getProviderById(@PathVariable("id") Long providerId){
        return ResponseEntity.ok(providerService.getProviderById(providerId));
    }

    @PostMapping("")
    public ResponseEntity<ProviderResponse> createProviderById(@Valid @RequestBody ProviderRequest providerRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(providerService.createProviderById(providerRequest));
    }

    @PutMapping(idUrl)
    public ResponseEntity<ProviderResponse> updateProviderById(@PathVariable("id") Long providerId, @Valid @RequestBody ProviderRequest providerRequest){
        return ResponseEntity.ok(providerService.updateProviderById(providerId, providerRequest));
    }

    @DeleteMapping(idUrl)
    public ResponseEntity<Boolean> deleteProviderById(@PathVariable("id") Long providerId){
        return ResponseEntity.ok(providerService.deleteProviderById(providerId));
    }

    @PatchMapping(idUrl)
    public ResponseEntity<ProviderResponse> patchProviderById(@PathVariable("id") Long providerId, @RequestBody Map<String, Object> patchData){
        return ResponseEntity.ok(providerService.patchProviderById(providerId, patchData));
    }
}
