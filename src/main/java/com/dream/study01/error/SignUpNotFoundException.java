package com.dream.study01.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SignUpNotFoundException extends RuntimeException{

    public SignUpNotFoundException(String exception){
        super(exception);
    }
}
