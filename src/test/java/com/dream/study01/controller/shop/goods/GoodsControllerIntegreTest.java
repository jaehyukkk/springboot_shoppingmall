package com.dream.study01.controller.shop.goods;

import com.dream.study01.domain.entity.shop.goods.Goods;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * 통합테스트
 * SpringBootTest.WebEnvironment.MOCK -> 가짜 톰켓서버
 * SpringBootTest.WebEnvironment.RANDOM_POR -> 실제 톰켓으로 테스트
 * AutoConfigureMockMvc MockMvc를 IoC에 등록해줌
 * 각각의 테스트함수가 종료돼리때마다 rollback을 해주기 위하여 @Transactional(각각의 함수를 독립적으로 테스트)
 */
@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GoodsControllerIntegreTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void save_테스트(){
    }
}
