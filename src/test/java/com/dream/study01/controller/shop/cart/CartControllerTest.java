package com.dream.study01.controller.shop.cart;

import com.dream.study01.config.JwtAccessDeniedHandler;
import com.dream.study01.config.JwtAuthenticationEntryPoint;
import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.cart.Cart;
import com.dream.study01.domain.entity.shop.cart.CartItem;
import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.dto.shop.cart.CartItemDto;
import com.dream.study01.jwt.TokenProvider;
import com.dream.study01.service.shop.cart.CartItemService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(CartController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CartControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    CartItemService cartItemService;
    @MockBean
    TokenProvider tokenProvider;
    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Test
    @WithMockUser(username = "10", password = "pwd", roles = "USER")
    @DisplayName("주문창 이동시 장바구니 가져오기")
    public void cartItemListTest() throws Exception {
        User user = new User(1L,"wogur1178@naver.com","password", "jaehyuk", "ROLE_USER,ROLE_ADMIN");
        Goods goods = Goods.builder()
                .id(1L)
                .price(10000)
                .title("테스트상품")
                .build();

        Cart cart = Cart.builder()
                .id(1L)
                .user(user)
                .build();

        CartItemDto cartItemDto = CartItemDto.builder()
                .id(953L)
                .itemCount(10)
                .cart(cart)
                .goods(goods)
                .build();

        CartItemDto cartItemDto1 = CartItemDto.builder()
                .id(952L)
                .itemCount(11)
                .cart(cart)
                .goods(goods)
                .build();

        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        cartItemDtoList.add(cartItemDto);
        cartItemDtoList.add(cartItemDto1);

        given(cartItemService.getCartItems(any(),any())).willReturn(cartItemDtoList);
        mockMvc.perform(get("/api/v1/cart-item/953,952"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].itemCount").value(10))
                .andDo(print());
    }

    @Test
    @DisplayName("장바구니 아이템 추가")
    @WithMockUser(username = "test", roles = {"ROLE_USER", "ROLE_ADMIN"})
    public void createCartItemExceptionTest() throws Exception{

        User user = new User(1L,"wogur1178@naver.com","password", "jaehyuk", "ROLE_USER,ROLE_ADMIN");
        Goods goods = Goods.builder()
                .id(1L)
                .price(10000)
                .title("테스트상품")
                .build();

        Cart cart = Cart.builder()
                .id(1L)
                .user(user)
                .build();

        CartItem cartItem = CartItem.builder()
                .id(953L)
                .itemCount(10)
                .cart(cart)
                .goods(goods)
                .build();

        given(cartItemService.createCartItem(1L,1L,1)).willReturn(cartItem);
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .param("goodsId", String.valueOf(1L)).param("itemCount", String.valueOf(10)))
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }

}