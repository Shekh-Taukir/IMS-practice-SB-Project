package com.practiceProject.Ims_Project.service.impl;

import com.practiceProject.Ims_Project.dto.AppointmentDto;
import com.practiceProject.Ims_Project.dto.AppointmentResponseDto;
import com.practiceProject.Ims_Project.repository.AppointmentRepository;
import com.practiceProject.Ims_Project.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<AppointmentResponseDto> getAppointmentDetailedList() {
        /*
        Appointment appointment1 = appointmentRepository.findAll().get(0);

        AppointmentResponseDto appointmentResponseDtos = modelMapper.map(appointment1,AppointmentResponseDto.class);

        System.out.println(" Result from DTO : " + appointmentResponseDtos.getPatient());
        return appointmentResponseDtos;
         */

        return appointmentRepository
                .findAll()
                .stream()
                .map(appointment -> modelMapper
                        .map(appointment,AppointmentResponseDto.class))
                .toList();
    }
}
