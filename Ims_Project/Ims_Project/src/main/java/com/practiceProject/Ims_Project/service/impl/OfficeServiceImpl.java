package com.practiceProject.Ims_Project.service.impl;

import com.practiceProject.Ims_Project.dto.OfficeDto;
import com.practiceProject.Ims_Project.repository.OfficeRepository;
import com.practiceProject.Ims_Project.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OfficeDto> getOfficeList() {
        return officeRepository
                .findAll()
                .stream()
                .map(office -> modelMapper
                        .map(office, OfficeDto.class))
                .toList();
    }
}
