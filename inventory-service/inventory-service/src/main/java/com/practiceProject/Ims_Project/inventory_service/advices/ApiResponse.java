package com.practiceProject.Ims_Project.inventory_service.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {
    private boolean success;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss dd/MM/yyyy")
    private String timeStamp;
    private T data;
    private String error;
}
