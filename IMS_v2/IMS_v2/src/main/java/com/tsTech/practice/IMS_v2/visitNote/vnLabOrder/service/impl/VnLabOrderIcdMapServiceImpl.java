package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service.impl;

import com.tsTech.practice.IMS_v2.setup.icd.repository.IcdRepository;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.mapper.VnLabOrderIcdMapMapper;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.repository.VnLabOrderIcdMapRepository;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.repository.VnLabOrderRepository;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service.VnLabOrderIcdMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/// //////////////////////////////////////////
//
// Name: Vn Lab Order Icd Map Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 11, 2026 || TaukirS (ER 1022 - vn lab order icd map entity coding)

/// //////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VnLabOrderIcdMapServiceImpl implements VnLabOrderIcdMapService {

    private final VnLabOrderIcdMapRepository repository;
    private final VnLabOrderRepository vnLabOrderRepository;
    private final IcdRepository icdRepository;

    private final VnLabOrderIcdMapMapper mapper;
}
