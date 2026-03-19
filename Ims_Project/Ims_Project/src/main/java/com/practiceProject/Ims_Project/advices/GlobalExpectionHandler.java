package com.practiceProject.Ims_Project.advices;

import com.practiceProject.Ims_Project.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Schema(hidden = true)
public class GlobalExpectionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError = new ApiError(exception.getMessage(),HttpStatus.NOT_FOUND);
        return ResponseEntity.status(apiError.getCode()).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception exception){
        ApiError apiError = new ApiError(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(apiError.getCode()).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInvalidArgumentException (MethodArgumentNotValidException exception){
        List<String> errors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error->error.getDefaultMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("Invalid inputs provided as request",HttpStatus.BAD_REQUEST,errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidEnumArgumentException(HttpMessageNotReadableException exception){
        ApiError apiError = new ApiError(exception.getMessage(),HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(apiError.getCode()).body(apiError);
    }
}
