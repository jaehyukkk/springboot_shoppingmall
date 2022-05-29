package com.dream.study01.error;

import lombok.Getter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private Map<String,String> details;
    private String detail;

    public ErrorDetails(Date timestamp, String message, Map<String, String> details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public ErrorDetails(Date timestamp, String message, String detail) {
        this.timestamp = timestamp;
        this.message = message;
        this.detail = detail;
    }

}
