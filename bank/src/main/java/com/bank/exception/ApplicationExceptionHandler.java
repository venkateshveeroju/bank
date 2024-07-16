package com.bank.exception;

import com.bank.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
//Global exception handler

public class ApplicationExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> runTimeError(RuntimeException e ){
        ErrorResponse errorResponse= new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity userName(){
        return new ResponseEntity("Errror while finding user", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity illegal(){
        return new ResponseEntity("Errror while finding user", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity ex(){
        return new ResponseEntity("Errror while finding user", HttpStatus.BAD_REQUEST);
    }
}
