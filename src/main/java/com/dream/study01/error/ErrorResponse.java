package com.dream.study01.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private String code;
    private String responseMessage;

    public ErrorResponse(String responseMessage, ErrorCode errorCode){
        this.responseMessage = responseMessage;
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.getErrorCode();
    }
}