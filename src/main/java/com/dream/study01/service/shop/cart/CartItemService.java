package com.dream.study01.service.shop.cart;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.cart.Cart;
import com.dream.study01.domain.entity.shop.cart.CartItem;
import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.domain.repository.UserRepository;
import com.dream.study01.domain.repository.shop.cart.CartItemRepository;
import com.dream.study01.domain.repository.shop.cart.CartRepository;
import com.dream.study01.domain.repository.shop.goods.GoodsRepository;
import com.dream.study01.dto.PageRequestDto;
import com.dream.study01.dto.shop.cart.CartItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CartItemService{

    private final CartItemRepository cartItemRepository;
    private final GoodsRepository goodsRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public CartItem createCartItem(Long userId,Long goodsId, int itemCount) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()->
                new IllegalArgumentException("카트가 생성되지 않았습니다."));
        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 상품입니다."));

        CartItemDto cartItemDto = CartItemDto.builder()
                .goods(goods)
                .cart(cart)
                .itemCount(itemCount)
                .build();

        return cartItemRepository.save(cartItemDto.toEntity());
    }

    public Page<CartItemDto> getCartItemList(Long userId, PageRequestDto pageRequestDto){
        Pageable pageable = pageRequestDto.getPageble(Sort.by("id").descending());
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()->
                new IllegalArgumentException("카트가 생성되지 않았습니다."));

        Page<CartItem> cartItemList = cartItemRepository.findByCart(cart, pageable);
        return cartItemList.map(CartItemDto::of);
    }

}
