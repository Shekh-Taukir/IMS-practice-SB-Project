package com.practiceProject.Ims_Project.service;

import com.practiceProject.Ims_Project.dto.OfficeDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface OfficeService {
    List<OfficeDto> getOfficeList();
}
