package com.dream.study01.controller.shop.cart;

import com.dream.study01.aop.UserRights;
import com.dream.study01.domain.entity.shop.cart.CartItem;
import com.dream.study01.dto.PageRequestDto;
import com.dream.study01.dto.shop.cart.CartItemDto;

import com.dream.study01.service.shop.cart.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Log4j2
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    @PostMapping("/api/v1/cart")
    @UserRights("ROLE_USER")
    public ResponseEntity<Object> createCartItem(@RequestParam(value="goodsId")Long goodsId, @RequestParam(value="itemCount") int itemCount, Principal principal){
        CartItem cartItem = cartItemService.createCartItem((long) Integer.parseInt(principal.getName()), goodsId, itemCount);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    //장바구니에 담긴 모든상품
    @GetMapping("/api/v1/cart-item")
    public ResponseEntity<Object> getCartItemList(PageRequestDto pageRequestDto, Principal principal){
        Long getUserId = (long) Integer.parseInt(principal.getName());
        Page<CartItemDto> cartItemList = cartItemService.getCartItemList(getUserId, pageRequestDto);
        return new ResponseEntity<>(cartItemList, HttpStatus.OK);
    }

    //주문창에 나갈 장바구니 아이템
    @GetMapping("/api/v1/cart-item/{cartIds}")
    public ResponseEntity<Object> getCartItems(@PathVariable("cartIds") Long[] cartIds, Principal principal){
        Long getUserId = (long) Integer.parseInt(principal.getName());
        List<Long> cartIdList = new ArrayList<>(Arrays.asList(cartIds));
        List<CartItemDto> cartItemDtoList = cartItemService.getCartItems(getUserId, cartIdList);

        return new ResponseEntity<>(cartItemDtoList, HttpStatus.OK);
    }

    //상품 갯수 업데이트
    @PutMapping("/api/v1/cart-item/count")
    public ResponseEntity<?> updateCartItemCount(@RequestParam("cartItemCount") int cartItemCount, @RequestParam("cartItemId") Long cartItemId){
        int cartItem = cartItemService.updateItemCount(cartItemCount, cartItemId);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/cart-item/{cartItemId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable("cartItemId") Long cartItemId){
        cartItemService.deleteCartItem(cartItemId);
        return new ResponseEntity<>("삭제 성공",HttpStatus.OK);
    }
}
