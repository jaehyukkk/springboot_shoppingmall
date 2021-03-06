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
import com.dream.study01.error.ErrorMessage;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class CartItemService{

    private final CartItemRepository cartItemRepository;
    private final GoodsRepository goodsRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    //장바구니에 아이템 추가
    public CartItem createCartItem(Long userId,Long goodsId, int itemCount) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()->
                new IllegalArgumentException("카트가 생성되지 않았습니다."));
        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 상품입니다."));

        CartItem cartItem = cartItemRepository.findByCartAndGoods(cart, goods);

        //장바구니에 이미 동일한 아이템이 있을때
        if(!Objects.isNull(cartItem)){
            Gson gson = new Gson();

            ErrorMessage errorMessage = ErrorMessage.builder()
                    .message("이미 등록된 상품")
                    .data(String.valueOf(cartItem.getId()))
                    .build();

            String message = gson.toJson(errorMessage);

            throw new RuntimeException(message);
        }

        CartItemDto cartItemDto = CartItemDto.builder()
                .goods(goods)
                .cart(cart)
                .itemCount(itemCount)
                .build();

        return cartItemRepository.save(cartItemDto.toEntity());
    }

    //해당 유저의 카트 아이템 전부 가져오기
    public Page<CartItemDto> getCartItemList(Long userId, PageRequestDto pageRequestDto){
        Pageable pageable = pageRequestDto.getPageble(Sort.by("id").descending());
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()->
                new IllegalArgumentException("카트가 생성되지 않았습니다."));

        Page<CartItem> cartItemList = cartItemRepository.findByCart(cart, pageable);
        return cartItemList.map(CartItemDto::of);
    }

    //주문화면으로 넘어갈 때 장바구니에서 선택한 아이템들
    public List<CartItemDto> getCartItems(Long userId, List<Long> cartIdList){
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()->
                new IllegalArgumentException("카트가 생성되지 않았습니다."));

        List<CartItem> cartItemList = cartItemRepository.findByCartAndInId(cart,cartIdList);
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        for(CartItem cartItem : cartItemList){
            CartItemDto cartItemDto = CartItemDto.builder()
                    .id(cartItem.getId())
                    .goods(cartItem.getGoods())
                    .itemCount(cartItem.getItemCount())
                    .build();
            cartItemDtoList.add(cartItemDto);
        }
        return cartItemDtoList;
    }


    //카트 아이템 삭제
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    //카트에 동일한 상품이 있을때 갯수만 올려주기
    @Transactional
    public int updateItemCount(int cartItemCount, Long cartItemId) {
        return cartItemRepository.updateItemCount(cartItemCount, cartItemId);
    }

}
