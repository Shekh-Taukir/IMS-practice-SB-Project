package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service.impl;

import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.mapper.VnLabOrderMapper;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.repository.VnLabOrderRepository;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.service.VnLabOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VnLabOrderServiceImpl implements VnLabOrderService {

    private final VnLabOrderRepository repository;
    private final VnLabOrderMapper mapper;

}
