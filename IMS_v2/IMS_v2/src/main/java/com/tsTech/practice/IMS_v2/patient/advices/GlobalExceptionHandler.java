package com.tsTech.practice.IMS_v2.patient.advices;

import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidArgumentException(MethodArgumentNotValidException exception){

        List<String> subErrors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map((error)->{
                    if (error instanceof FieldError fieldError){
                        String fieldName = fieldError.getField();

                        return messageSource.getMessage(
                                error.getDefaultMessage(),
                                new Object[]{fieldName},
                                error.getDefaultMessage(),
                                LocaleContextHolder.getLocale()
                        );
                    }
                    else
                        return error.getDefaultMessage();})
                .toList();

        return getApiResponseObj(HttpStatus.BAD_REQUEST, "Invalid Input Arguments provided!!", subErrors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(ResourceNotFoundException exception){
        return getApiResponseObj(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(RuntimeException exception){
        return getApiResponseObj(HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        return getApiResponseObj(HttpStatus.BAD_REQUEST,exception.getMessage()+" | "+exception.getLocalizedMessage());
    }

    /// INternal FUnctions

    private ResponseEntity<ApiResponse<?>> getApiResponseObj(HttpStatus httpStatus, String message) {
        return getApiResponseObj(httpStatus, message,null);
    }

    private ResponseEntity<ApiResponse<?>> getApiResponseObj(HttpStatus httpStatus, String message, List<String> subErrors) {
        ApiError error = ApiError.builder()
                .message(message)
                .status(httpStatus)
                .subErrors(subErrors)
                .build();

        return ResponseEntity.status(httpStatus).body(new ApiResponse<>(error));
    }
}
