package com.tsTech.practice.IMS_v2.setup.controller;

import com.tsTech.practice.IMS_v2.setup.dto.request.IcdRequest;
import com.tsTech.practice.IMS_v2.setup.dto.response.IcdResponse;
import com.tsTech.practice.IMS_v2.setup.service.IcdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/////////////////////////////////////////////
//
// Name: Icd Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 17, 2026 || TaukirS (ER 1014 - icd entity setup coding)
/////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/icd")
public class IcdController {

    private final IcdService icdService;
    private static final String ID_URL = "/{id}";

    @PostMapping("")
    public ResponseEntity<IcdResponse> createIcd(@Valid @RequestBody IcdRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(icdService.createIcd(request));
    }

    @GetMapping(ID_URL)
    public ResponseEntity<IcdResponse> getIcdById(@PathVariable("id") Long icdId){
        return ResponseEntity.ok(icdService.getIcdById(icdId));
    }

    @GetMapping()
    public ResponseEntity<List<IcdResponse>> getIcdList(){
        return ResponseEntity.ok(icdService.getIcdList());
    }

    @PutMapping(ID_URL)
    public ResponseEntity<IcdResponse> updateIcd(@PathVariable("id") Long icdId, @Valid @RequestBody IcdRequest request){
        return ResponseEntity.ok(icdService.updateIcd(icdId, request));
    }

    @PatchMapping("/expire"+ID_URL)
    public ResponseEntity<IcdResponse> expiryIcdById(@PathVariable("id") Long icdId){
        return ResponseEntity.ok(icdService.expiryIcdById(icdId));
    }

    @PatchMapping(ID_URL)
    public ResponseEntity<IcdResponse> patchUpdateIcdById(@PathVariable("id") Long icdId, @RequestBody Map<String, Object>patchData){
        return ResponseEntity.ok(icdService.patchUpdateIcdById(icdId, patchData));
    }
}
