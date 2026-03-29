package com.practiceProject.Ims_Project.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"success", "timeStamp", "data", "error"})
public class ApiResponse<T> {

    @JsonFormat(pattern = "hh:mm:ss dd/MM/yyyy")
    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;
    private boolean success;

    public ApiResponse(ApiError error) {
        ///default constructor called, to set timestamp to ApiResponse object, and success flag
        this(false);
        this.error = error;
    }

    public ApiResponse(T data) {
        ///default constructor called, to set timestamp to ApiResponse object
        this(true);
        this.data = data;
    }

    public ApiResponse(boolean successFlag) {
        this.timeStamp = LocalDateTime.now();
        this.success = successFlag;
    }
}
