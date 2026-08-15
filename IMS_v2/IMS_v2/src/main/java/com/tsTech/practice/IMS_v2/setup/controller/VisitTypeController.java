package com.tsTech.practice.IMS_v2.setup.controller;

import com.tsTech.practice.IMS_v2.setup.dto.request.VisitTypeRequest;
import com.tsTech.practice.IMS_v2.setup.dto.response.VisitTypeResponse;
import com.tsTech.practice.IMS_v2.setup.service.VisitTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/////////////////////////////////////////////
//
// Name: Visit Type Controller
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Aug 15, 2026 || TaukirS (ER 1012 - visit type entity code)
/////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/visit-type")
public class VisitTypeController {

    private final VisitTypeService visitTypeService;
    private static final String ID_URL = "/{id}";

    @PostMapping("")
    public ResponseEntity<VisitTypeResponse> createVisitType(@Valid @RequestBody VisitTypeRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(visitTypeService.createVisitType(request));
    }

    @GetMapping(ID_URL)
    public ResponseEntity<VisitTypeResponse> getVisitTypeById(@PathVariable("id") Long visitTypeId){
        return ResponseEntity.ok(visitTypeService.getVisitTypeById(visitTypeId));
    }

    @GetMapping("")
    public ResponseEntity<List<VisitTypeResponse>> getVisitTypeList(){
        return ResponseEntity.ok(visitTypeService.getVisitTypeList());
    }

    @PutMapping(ID_URL)
    public ResponseEntity<VisitTypeResponse> updateVisitType(@PathVariable("id") Long visitTypeId, @Valid @RequestBody VisitTypeRequest request){
        return ResponseEntity.ok(visitTypeService.updateVisitType(visitTypeId, request));
    }

    @DeleteMapping(ID_URL)
    public ResponseEntity<Void> deleteVisitType(@PathVariable("id") Long visitTypeId){
        visitTypeService.deleteVisitType(visitTypeId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(ID_URL)
    public ResponseEntity<VisitTypeResponse> patchUpdateVisitType(@PathVariable("id") Long visitTypeId, @RequestBody Map<String, Object> patchData){
        return ResponseEntity.ok(visitTypeService.patchUpdateVisitType(visitTypeId, patchData));
    }
}
