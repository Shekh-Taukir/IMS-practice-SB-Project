package com.practiceProject.Ims_Project.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
//@Schema(hidden = true)
public class ApiResponse<T> {

    @JsonFormat(pattern = "hh:mm:ss dd/MM/yyyy")
    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;

    public ApiResponse(ApiError error) {
        //default constructor called, to set timestamp to Apiresponse object
        this();
        this.error = error;
    }

    public ApiResponse(T data) {
        //default constructor called, to set timestamp to Apiresponse object
        this();
        this.data = data;
    }

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }
}
