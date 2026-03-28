package com.practiceProject.Ims_Project.controller;

import com.practiceProject.Ims_Project.dto.OfficeDto;
import com.practiceProject.Ims_Project.service.OfficeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Office Master", description = "Office Master Api's")
@RequestMapping("/office")
@RequiredArgsConstructor
@RestController
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping("/list")
    public ResponseEntity<List<OfficeDto>> getOfficeList(){
        return ResponseEntity.ok(officeService.getOfficeList());
    }
}
