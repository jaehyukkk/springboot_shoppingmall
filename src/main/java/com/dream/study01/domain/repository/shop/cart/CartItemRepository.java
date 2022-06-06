package com.dream.study01.domain.repository.shop.cart;

import com.dream.study01.domain.entity.shop.cart.Cart;
import com.dream.study01.domain.entity.shop.cart.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    public Page<CartItem> findByCart(Cart cart, Pageable pageable);
}
