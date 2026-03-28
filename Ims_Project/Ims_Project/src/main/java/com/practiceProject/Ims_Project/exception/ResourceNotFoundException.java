package com.practiceProject.Ims_Project.exception;


import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
        // Mar 27, 2026 TaukirS (ER 1102 - Completing all necessary modules API's) Added errorLog line in ResourceNotFound Exception class
        log.error("Error Log | "+String.valueOf(LocalDateTime.now())+" | "+message);
    }
}
