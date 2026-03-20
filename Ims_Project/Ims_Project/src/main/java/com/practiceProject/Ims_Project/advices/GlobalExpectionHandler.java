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
//@Schema(hidden = true)
public class GlobalExpectionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError = new ApiError(exception.getMessage(),HttpStatus.NOT_FOUND);
        return returnResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneralException(Exception exception){
        ApiError apiError = new ApiError(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return returnResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidArgumentException (MethodArgumentNotValidException exception){
        List<String> errors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error->error.getDefaultMessage())
                .toList();

        ApiError apiError = new ApiError("Invalid inputs provided as request",HttpStatus.BAD_REQUEST,errors);
        return returnResponseEntity(apiError);

//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(new ApiError("Invalid inputs provided as request",HttpStatus.BAD_REQUEST,errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidEnumArgumentException(HttpMessageNotReadableException exception){
        ApiError apiError = new ApiError(exception.getMessage(),HttpStatus.BAD_REQUEST);
//        return ResponseEntity.status(apiError.getCode()).body(apiError);
        return returnResponseEntity(apiError);
    }


    /// INternal Services methods
    public ResponseEntity<ApiResponse<?>> returnResponseEntity(ApiError apiError){
        return ResponseEntity.status(apiError.getCode()).body(new ApiResponse<>(apiError));
    }
}
