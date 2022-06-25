package com.dream.study01.dto.shop.cart;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.cart.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CartDto {

    private Long id;
    private User user;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Cart toEntity() {
        return Cart.builder()
                .id(id)
                .user(user)
                .build();
    }

    @Builder
    public CartDto(Long id, User user, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.user = user;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
