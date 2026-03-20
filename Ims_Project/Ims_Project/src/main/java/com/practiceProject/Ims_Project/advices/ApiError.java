package com.practiceProject.Ims_Project.advices;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
//@Schema(hidden = true)
public class ApiError {
    private String message;
    private HttpStatus code;
    private List<String> subErrors;

    public ApiError(String message, HttpStatus code) {
        this.message = message;
        this.code = code;
    }
}
