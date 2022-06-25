package com.dream.study01.dto.shop.cart;

import com.dream.study01.domain.entity.shop.cart.Cart;
import com.dream.study01.domain.entity.shop.cart.CartItem;
import com.dream.study01.domain.entity.shop.goods.Goods;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class CartItemDto {

    private Long id;
    private Goods goods;
    private Cart cart;
    private int itemCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public CartItem toEntity(){
        return CartItem.builder()
                .id(id)
                .goods(goods)
                .cart(cart)
                .itemCount(itemCount)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();
    }

    @Builder
    public CartItemDto(Long id, Goods goods, Cart cart, int itemCount, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.goods = goods;
        this.cart = cart;
        this.itemCount = itemCount;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public static CartItemDto of(CartItem cartItem){
        return new CartItemDto(
                cartItem.getId(),
                cartItem.getGoods(),
                cartItem.getCart(),
                cartItem.getItemCount(),
                cartItem.getCreatedDate(),
                cartItem.getUpdatedDate());
    }
}
