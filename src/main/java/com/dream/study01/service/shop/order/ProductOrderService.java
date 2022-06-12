package com.dream.study01.service.shop.order;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.coupon.IssuanceCoupon;
import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.domain.entity.shop.order.ProductOrder;
import com.dream.study01.domain.entity.shop.order.ProductOrderItem;
import com.dream.study01.domain.repository.UserRepository;
import com.dream.study01.domain.repository.shop.coupon.IssuanceCouponRepository;
import com.dream.study01.domain.repository.shop.goods.GoodsRepository;
import com.dream.study01.domain.repository.shop.order.ProductOrderItemRepository;
import com.dream.study01.domain.repository.shop.order.ProductOrderRepository;
import com.dream.study01.dto.shop.order.PaymentCancelResponseDto;
import com.dream.study01.dto.shop.order.ProductOrderItemDto;
import com.dream.study01.dto.shop.order.ProductOrderRequestDto;
import com.dream.study01.dto.shop.order.ProductOrderResponseDto;
import com.dream.study01.error.ErrorCode;
import com.dream.study01.error.PaymentException;
import com.dream.study01.service.shop.iamport.IamportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderItemRepository productOrderItemRepository;
    private final UserRepository userRepository;
    private final IssuanceCouponRepository issuanceCouponRepository;
    private final GoodsRepository goodsRepository;
    private final IamportService iamportService;

    public ProductOrderService(ProductOrderRepository productOrderRepository, ProductOrderItemRepository productOrderItemRepository, UserRepository userRepository, IssuanceCouponRepository issuanceCouponRepository, GoodsRepository goodsRepository, IamportService iamportService) {
        this.productOrderRepository = productOrderRepository;
        this.productOrderItemRepository = productOrderItemRepository;
        this.userRepository = userRepository;
        this.issuanceCouponRepository = issuanceCouponRepository;
        this.goodsRepository = goodsRepository;
        this.iamportService = iamportService;
    }

    @Transactional
    public ProductOrder createOrder(ProductOrderRequestDto productOrderRequestDto, Long userId){
        PaymentCancelResponseDto paymentCancelResponseDto = new PaymentCancelResponseDto(productOrderRequestDto.getImpUid(), productOrderRequestDto.getPrice());

        User user = userRepository.findById(userId).orElseThrow(() ->
                new PaymentException("유저 조회 에러, 결제 취소", ErrorCode.PAYMENT_ERROR, paymentCancelResponseDto ));

        productOrderRequestDto.setUser(user);

        log.info(productOrderRequestDto.getIssuanceCouponId());
        if(!Objects.isNull(productOrderRequestDto.getIssuanceCouponId())){
            IssuanceCoupon issuanceCoupon = issuanceCouponRepository.findById(productOrderRequestDto.getIssuanceCouponId()).orElseThrow(() ->
                    new PaymentException("쿠폰 사용 오류, 결제 취소", ErrorCode.PAYMENT_ERROR, paymentCancelResponseDto));

            productOrderRequestDto.setIssuanceCoupon(issuanceCoupon);
        }

        ProductOrder productOrder = productOrderRepository.save(productOrderRequestDto.toEntity());

        List<Goods> goodsList = goodsRepository.findGoodsIn(productOrderRequestDto.getGoodsIdList());
        List<Integer> itemCountList = productOrderRequestDto.getItemCountList();

        for (int i = 0; i < goodsList.size(); i++) {

            ProductOrderItemDto productOrderItemDto = new ProductOrderItemDto();
                productOrderItemDto.setGoods(goodsList.get(i));
                productOrderItemDto.setItemCount(itemCountList.get(i));
                productOrderItemDto.setProductOrder(productOrder);

            ProductOrderItem productOrderItem = productOrderItemRepository.save(productOrderItemDto.toEntity());
            productOrder.addOrderItem(productOrderItem);
        }

        return productOrder;
    }

    public List<ProductOrderResponseDto> getProductOrderList(Long userId) {
        List<ProductOrder> productOrderList = productOrderRepository.findAllFetchBy(userId);
        List<ProductOrderResponseDto> productOrderResponseDtoList = new ArrayList<>();
        for (ProductOrder productOrder : productOrderList) {
            productOrderResponseDtoList.add(ProductOrderResponseDto.of(productOrder));
        }
        return productOrderResponseDtoList;
    }

    public List<ProductOrderItemDto> getProductOrderItemList(Long userId) {
        List<ProductOrderItem> productOrderItemList = productOrderItemRepository.findAllByUser(userId);
        List<ProductOrderItemDto> productOrderItemDtoList = new ArrayList<>();
        for (ProductOrderItem productOrderItem : productOrderItemList) {
            productOrderItemDtoList.add(ProductOrderItemDto.of(productOrderItem));
        }
        return productOrderItemDtoList;
    }

    public ProductOrderResponseDto getProductOrder(Long userId, Long productOrderId) {
        ProductOrder productOrder = productOrderRepository.findAllFetchByUserAndId(userId, productOrderId);
        log.info("price : " + productOrder.getPrice());
        return ProductOrderResponseDto.of(productOrder);
    }

    public boolean paymentCheck(String access_token, ProductOrderRequestDto productOrderRequestDto) throws IOException {

        int realAmount = 0;
        int couponDiscountAmount = 0;
        Long issuanceCouponId = productOrderRequestDto.getIssuanceCouponId();

        if(!Objects.isNull(issuanceCouponId)){

            PaymentCancelResponseDto paymentCancelResponseDto =
                    new PaymentCancelResponseDto(productOrderRequestDto.getImpUid(), productOrderRequestDto.getPrice());

            IssuanceCoupon issuanceCoupon = issuanceCouponRepository.findById(issuanceCouponId).orElseThrow(() ->
                    new PaymentException("쿠폰 조회 에러, 결제 취소", ErrorCode.PAYMENT_ERROR, paymentCancelResponseDto));

            couponDiscountAmount = issuanceCoupon.getCoupon().getPrice();
        }

        int paymentAmount = iamportService.paymentInfo(productOrderRequestDto.getImpUid(), access_token);

        List<Goods> goodsList = goodsRepository.findGoodsIn(productOrderRequestDto.getGoodsIdList());
        List<Integer> itemCountList = productOrderRequestDto.getItemCountList();

        for(int i = 0 ; i < goodsList.size(); i ++){
            realAmount += goodsList.get(i).getPrice() * itemCountList.get(i);
        }

        return paymentAmount == realAmount - couponDiscountAmount;
    }
}
