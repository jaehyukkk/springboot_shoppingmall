package com.dream.study01.controller.shop.goods;

import com.dream.study01.config.JwtAccessDeniedHandler;
import com.dream.study01.config.JwtAuthenticationEntryPoint;
import com.dream.study01.jwt.TokenProvider;
import com.dream.study01.service.shop.goods.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;


@Slf4j
@WebMvcTest(GoodsController.class)
@MockBean(JpaMetamodelMappingContext.class)
class GoodsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean //IoC환경에 bean등록됌
    private GoodsService goodsService;
    @MockBean
    TokenProvider tokenProvider;
    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Test
    public void save_테스트(){

    }
}