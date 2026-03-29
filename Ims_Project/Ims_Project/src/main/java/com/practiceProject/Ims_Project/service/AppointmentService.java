package com.practiceProject.Ims_Project.service;

import com.practiceProject.Ims_Project.dto.AppointmentDto;
import com.practiceProject.Ims_Project.dto.AppointmentResponseDto;

import java.util.List;

public interface AppointmentService {

    List<AppointmentDto> getAppointmentList();

    List<AppointmentResponseDto> getAppointmentDetailedList();
}
