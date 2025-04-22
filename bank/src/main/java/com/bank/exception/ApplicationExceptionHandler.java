package com.bank.exception;

import com.bank.util.ErrorResponse;
import jakarta.transaction.InvalidTransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> runTimeError(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);  // 400 - Bad Request
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleRoleNotFound(RoleNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);  // 404 - Not Found
    }

    @ExceptionHandler(AccountCreationException.class)
    public ResponseEntity<String> handleAccountCreationError(AccountCreationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // 500 - Internal Server Error
    }


    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity noSuchElementEx(NoSuchElementException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message", ex.getMessage());
        return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity userNameNotFoundEx(UsernameNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message", ex.getMessage());
        return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity illegal(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(400, ex.getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity ex(Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message", ex.getMessage());
        return new ResponseEntity(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = InvalidTransactionException.class)
    public ResponseEntity invalidBalance(InvalidTransactionException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message", ex.getMessage());
        return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
    }
}
