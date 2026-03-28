package com.practiceProject.Ims_Project.service.impl;

import com.practiceProject.Ims_Project.dto.AppointmentDto;
import com.practiceProject.Ims_Project.repository.AppointmentRepository;
import com.practiceProject.Ims_Project.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AppointmentDto> getAppointmentList() {
        return appointmentRepository
                .findAll()
                .stream()
                .map((appointment) -> modelMapper
                        .map(appointment, AppointmentDto.class))
                .toList();
    }
}
