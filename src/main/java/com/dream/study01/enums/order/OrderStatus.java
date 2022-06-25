package com.dream.study01.enums.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderStatus {
    READY("배송준비중"), GO("배송중"), SUCCESS("배송완료");

    final private String name;
    private OrderStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public static OrderStatus nameOf(String name) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        return null;
    }
}
