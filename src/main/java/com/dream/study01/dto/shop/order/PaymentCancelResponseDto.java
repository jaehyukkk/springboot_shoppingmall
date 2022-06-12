package com.dream.study01.dto.shop.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCancelResponseDto {

    private String access_token;
    private String imp_uid;
    private int amount;
    private String reason;

    public PaymentCancelResponseDto(String imp_uid, int amount) {
        this.imp_uid = imp_uid;
        this.amount = amount;
    }
}
