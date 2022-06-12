package com.dream.study01.error;

import com.dream.study01.dto.shop.order.PaymentCancelResponseDto;
import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException{

    private final ErrorCode errorCode;
    private final PaymentCancelResponseDto paymentCancelResponseDto;

    public PaymentException(String message, ErrorCode errorCode, PaymentCancelResponseDto paymentCancelResponseDto) {
        super(message);
        this.errorCode = errorCode;
        this.paymentCancelResponseDto = paymentCancelResponseDto;
    }

}
