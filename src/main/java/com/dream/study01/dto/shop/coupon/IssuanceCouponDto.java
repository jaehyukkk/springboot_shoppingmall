package com.dream.study01.dto.shop.coupon;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.coupon.Coupon;
import com.dream.study01.domain.entity.shop.coupon.IssuanceCoupon;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IssuanceCouponDto {

    private Long id;
    private Coupon coupon;
    private User user;
    private boolean isUse;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime updateDate;

    public IssuanceCoupon toEntity(){

        return IssuanceCoupon.builder()
                .id(id)
                .coupon(coupon)
                .user(user)
                .isUse(isUse)
                .build();
    }

    @Builder

    public IssuanceCouponDto(Long id, Coupon coupon, User user, boolean isUse, LocalDateTime createdDate, LocalDateTime updateDate) {

        this.id = id;
        this.coupon = coupon;
        this.user = user;
        this.isUse = isUse;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public static IssuanceCouponDto of(IssuanceCoupon issuanceCoupon){
        return new IssuanceCouponDto(
                issuanceCoupon.getId(),
                issuanceCoupon.getCoupon(),
                issuanceCoupon.getUser(),
                issuanceCoupon.isUse(),
                issuanceCoupon.getCreatedDate(),
                issuanceCoupon.getUpdatedDate());
    }
}
