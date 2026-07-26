package com.tsTech.practice.IMS_v2.common.advices;

import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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

/////////////////////////////////////////////
//
// Name: Global Exception Handler
//
// Description:
//
// Version history:
//
// v1.1 || type : New FUnc || Jun 18, 2026 || TaukirS (ER 1002 - patient mst apis)
// v1.2 || type : Change || Jul 23, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

//Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes)
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidArgumentException(MethodArgumentNotValidException exception, HttpServletRequest servletRequest){

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

        //Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.error("API => {}:{} | Invalid Argument Exception occurred while adding / updating data | exception : ",servletRequest.getMethod(), servletRequest.getRequestURL(), exception);

        return getApiResponseObj(HttpStatus.BAD_REQUEST, "Invalid Input Arguments provided!!", subErrors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException exception, HttpServletRequest servletRequest){
        //Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.error("API => {}:{} | Resource not found Exception | exception : ",servletRequest.getMethod(),servletRequest.getRequestURL(),exception);

        return getApiResponseObj(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(RuntimeException exception, HttpServletRequest servletRequest){
        //Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.error("API => {}:{} | Internal Server Error occurred | exception : ",servletRequest.getMethod(),servletRequest.getRequestURL(),exception);

        return getApiResponseObj(HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest servletRequest){
        //Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.error("API => {}:{} | Http Message Not Readable Exception due to invalid user input in api | exception : ",servletRequest.getMethod(),servletRequest.getRequestURL(),exception);

        return getApiResponseObj(HttpStatus.BAD_REQUEST,exception.getMessage()+" | "+exception.getLocalizedMessage());
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadableException(EntityExistsException exception, HttpServletRequest servletRequest){
        //Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.error("API => {}:{} | Duplicate Entity exception while adding / altering data | exception : ",servletRequest.getMethod(),servletRequest.getRequestURL(),exception);

        return getApiResponseObj(HttpStatus.BAD_REQUEST,exception.getMessage());
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
