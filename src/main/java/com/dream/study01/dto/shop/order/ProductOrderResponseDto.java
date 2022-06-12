package com.dream.study01.dto.shop.order;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.coupon.IssuanceCoupon;
import com.dream.study01.domain.entity.shop.order.ProductOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProductOrderResponseDto {

    private Long id;
    private User user;
    private String paymentUserName;
    private Integer price;
    private String impUid;
    private String memo;
    private String address;
    private Integer phone;
    private List<ProductOrderItemDto> orderItems;
    private IssuanceCoupon issuanceCoupon;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public static ProductOrderResponseDto of(ProductOrder productOrder){
        ProductOrderResponseDto productOrderResponseDto = new ProductOrderResponseDto();
        productOrderResponseDto.setId(productOrder.getId());
        productOrderResponseDto.setUser(productOrder.getUser());
        productOrderResponseDto.setPaymentUserName(productOrder.getPaymentUserName());
        productOrderResponseDto.setImpUid(productOrder.getImpUid());
        productOrderResponseDto.setMemo(productOrder.getMemo());
        productOrderResponseDto.setAddress(productOrder.getAddress());
        productOrderResponseDto.setPhone(productOrder.getPhone());
        productOrderResponseDto.setOrderItems(productOrder.getProductOrderItems().stream().map(ProductOrderItemDto::of).collect(Collectors.toList()));
        productOrderResponseDto.setCreatedDate(productOrder.getCreatedDate());
        productOrderResponseDto.setUpdatedDate(productOrder.getUpdatedDate());
        productOrderResponseDto.setIssuanceCoupon(productOrder.getIssuanceCoupon());
        productOrderResponseDto.setPrice(productOrder.getPrice());
        return productOrderResponseDto;
    }
}
