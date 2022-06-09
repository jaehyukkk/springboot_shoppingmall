package com.dream.study01.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {

    private String message;
    private String data;

    @Builder
    public ErrorMessage(String message, String data) {
        this.message = message;
        this.data = data;
    }
}
