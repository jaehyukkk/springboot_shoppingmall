package com.dream.study01.dto.shop.order;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.coupon.IssuanceCoupon;
import com.dream.study01.domain.entity.shop.order.ProductOrder;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductOrderRequestDto {

    private Long id;
    private User user;
    private String paymentUserName;
    private Integer price;
    private String impUid;
    private String memo;
    private String address;
    private Integer phone;
    private Long issuanceCouponId;
    private IssuanceCoupon issuanceCoupon;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<Long> goodsIdList;
    private List<Integer> itemCountList;


    public ProductOrder toEntity(){
        return ProductOrder.builder()
                .id(id)
                .user(user)
                .paymentUserName(paymentUserName)
                .price(price)
                .impUid(impUid)
                .memo(memo)
                .address(address)
                .phone(phone)
                .issuanceCoupon(issuanceCoupon)
                .build();
    }

}
