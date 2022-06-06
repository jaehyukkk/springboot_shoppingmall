package com.dream.study01.service.shop.cart;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.cart.Cart;
import com.dream.study01.domain.repository.shop.cart.CartRepository;
import com.dream.study01.dto.shop.cart.CartDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }

    public Cart createCart(User user){
        CartDto cartDto = CartDto.builder()
                .user(user)
                .build();
        return cartRepository.save(cartDto.toEntity());
    }

}
