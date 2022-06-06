package com.dream.study01.controller.shop.cart;

import com.dream.study01.domain.entity.shop.cart.CartItem;
import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.dto.PageRequestDto;
import com.dream.study01.dto.shop.cart.CartItemDto;
import com.dream.study01.dto.shop.goods.GoodsDto;
import com.dream.study01.dto.shop.goods.GoodsRequestDto;
import com.dream.study01.service.shop.cart.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Log4j2
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    @PostMapping("/api/v1/cart")
    public ResponseEntity<Object> createCartItem(@RequestParam(value="goodsId")Long goodsId, @RequestParam(value="itemCount") int itemCount, Principal principal){
        CartItem cartItem = cartItemService.createCartItem((long) Integer.parseInt(principal.getName()), goodsId, itemCount);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/cart-item")
    public ResponseEntity<Object> getCartItemList(PageRequestDto pageRequestDto, Principal principal){
        Long getUserId = (long) Integer.parseInt(principal.getName());
        Page<CartItemDto> cartItemList = cartItemService.getCartItemList(getUserId, pageRequestDto);
        return new ResponseEntity<>(cartItemList, HttpStatus.OK);
    }
}
