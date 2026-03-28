package com.practiceProject.Ims_Project.service;

import com.practiceProject.Ims_Project.dto.AppointmentDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface AppointmentService {

    List<AppointmentDto> getAppointmentList();
}
