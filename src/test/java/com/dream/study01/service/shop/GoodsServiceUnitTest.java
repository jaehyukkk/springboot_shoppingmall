package com.dream.study01.service.shop;

import com.dream.study01.domain.repository.shop.goods.GoodsRepository;
import com.dream.study01.service.shop.goods.GoodsService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * 단위테스트 ( Service와 관련된것들만 메모리에 띄우면 됌 )
 * service를 테스트 하기 위해서 repository까지 띄우면 결국 단위테스트가 아니게 되는데
 * 그걸 해결 하기 위하여 Mock을 사용 => 가짜객체를 만들어줌
 */
@ExtendWith(MockitoExtension.class)
public class GoodsServiceUnitTest {

    @InjectMocks // GoodsService객체가 만들어 질 때 GoodsServiceUnitTest 파일에 @Mock으로 등록된 모든것들을 주입받음
    private GoodsService goodsService;

    @Mock
    private GoodsRepository goodsRepository;
}
