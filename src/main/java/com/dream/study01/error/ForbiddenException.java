package com.dream.study01.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class ForbiddenException extends RuntimeException{

    private ErrorCode errorCode;

    public ForbiddenException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
