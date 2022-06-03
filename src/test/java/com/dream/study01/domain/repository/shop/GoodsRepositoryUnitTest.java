package com.dream.study01.domain.repository.shop;

import com.dream.study01.domain.repository.shop.goods.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // 가짜DB, (NONE 실제 DB)
@DataJpaTest //repository들을 다 IoC에 등록해줌 goodsRepository에 Mock을 등록 할 필요가 없는 이유
public class GoodsRepositoryUnitTest {

    @Autowired
    private GoodsRepository goodsRepository;
}
