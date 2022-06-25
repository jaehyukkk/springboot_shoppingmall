package com.dream.study01.dto.shop.coupon;

import com.dream.study01.domain.entity.shop.coupon.Coupon;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CouponDto {

    private Long id;
    @NotBlank(message = "쿠폰 이름은 필수입력값입니다.")
    private String title;
    @NotBlank(message = "쿠폰 설명은 필수입력값입니다.")
    private String description;
    @NotNull(message = "쿠폰 할인가격은 필수입력값입니다.")
    private Integer price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime updateDate;

    public Coupon toEntity(){

        return Coupon.builder()
                .id(id)
                .title(title)
                .description(description)
                .price(price)
                .build();
    }

    @Builder
    public CouponDto(Long id, String title, String description, Integer price, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public static CouponDto of(Coupon coupon){
        return new CouponDto(
                coupon.getId(),
                coupon.getTitle(),
                coupon.getDescription(),
                coupon.getPrice(),
                coupon.getCreatedDate(),
                coupon.getUpdatedDate());
    }
}
