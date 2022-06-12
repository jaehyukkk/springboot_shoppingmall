package com.dream.study01.controller.advice;


import com.dream.study01.error.ErrorResponse;
import com.dream.study01.error.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyAdvice {

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> ForbiddenException(ForbiddenException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage(),ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtimeException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
