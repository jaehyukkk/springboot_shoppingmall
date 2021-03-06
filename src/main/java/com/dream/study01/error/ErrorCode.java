package com.dream.study01.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
    EMAIL_DUPLICATION(400,"MEMBER-ERR-400","EMAIL DUPLICATED"),
    FORBIDDEN(403, "FORBIDDEN-ERR-403", "FORBIDDEN"),
    PAYMENT_ERROR(400, "PAYMENT-ERROR-400", "PAYMENT ERROR"),
    VALID_ERROR(400,"VALID-ERROR-400", "VALID ERROR" )
    ;

    private final int status;
    private final String errorCode;
    private final String message;
}