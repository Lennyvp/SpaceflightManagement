package com.lenart.spaceflightmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> exception(NotFoundException exception) {
        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<Object> conflictException(ConflictException exception){
        return new ResponseEntity<>("Conflict Exception", HttpStatus.CONFLICT);
    }
}
