package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.controller;

import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service.VnLabOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/// //////////////////////////////////////////
//
// Name: VisitNote LabOrder Controller
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 08, 2026 || TaukirS (ER 1020 - vn lab order entity coding)
/// //////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/visit/{pn_Id}/vn-labOrder")
public class VnLabOrderController {
    private final VnLabOrderService vnLabOrderService;

    

}
