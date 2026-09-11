package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.controller;

import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service.VnLabOrderIcdMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vn-labOrder/{vn_labOrder_id}/icd-map")
public class VnLabOrderIcdMapController {

    private final VnLabOrderIcdMapService service;
    private static final String ID_URL = "/{id}";

    
}
