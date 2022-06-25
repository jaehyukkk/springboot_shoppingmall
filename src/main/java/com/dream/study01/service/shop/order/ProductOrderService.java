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
import com.dream.study01.dto.PageRequestDto;
import com.dream.study01.dto.shop.order.PaymentCancelResponseDto;
import com.dream.study01.dto.shop.order.ProductOrderItemDto;
import com.dream.study01.dto.shop.order.ProductOrderRequestDto;
import com.dream.study01.dto.shop.order.ProductOrderResponseDto;
import com.dream.study01.enums.order.OrderStatus;
import com.dream.study01.error.ErrorCode;
import com.dream.study01.error.PaymentException;
import com.dream.study01.service.shop.coupon.IssuanceCouponService;
import com.dream.study01.service.shop.iamport.IamportService;
import com.dream.study01.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Service
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderItemRepository productOrderItemRepository;
    private final UserRepository userRepository;
    private final IssuanceCouponService issuanceCouponService;
    private final IssuanceCouponRepository issuanceCouponRepository;
    private final GoodsRepository goodsRepository;
    private final IamportService iamportService;

    public ProductOrderService(ProductOrderRepository productOrderRepository, ProductOrderItemRepository productOrderItemRepository, UserRepository userRepository ,IssuanceCouponService issuanceCouponService, IssuanceCouponRepository issuanceCouponRepository, GoodsRepository goodsRepository, IamportService iamportService) {
        this.productOrderRepository = productOrderRepository;
        this.productOrderItemRepository = productOrderItemRepository;
        this.userRepository = userRepository;
        this.issuanceCouponService = issuanceCouponService;
        this.issuanceCouponRepository = issuanceCouponRepository;
        this.goodsRepository = goodsRepository;
        this.iamportService = iamportService;
    }

    //들어온 주문 정보를 저장
    @Transactional
    public ProductOrder createOrder(ProductOrderRequestDto productOrderRequestDto, Long userId){
        PaymentCancelResponseDto paymentCancelResponseDto = new PaymentCancelResponseDto(productOrderRequestDto.getImpUid(), productOrderRequestDto.getPrice());

        User user = userRepository.findById(userId).orElseThrow(() ->
                new PaymentException("유저 조회 에러, 결제 취소", ErrorCode.PAYMENT_ERROR, paymentCancelResponseDto ));

        productOrderRequestDto.setUser(user);

        if(!Objects.isNull(productOrderRequestDto.getIssuanceCouponId())){
            IssuanceCoupon issuanceCoupon = issuanceCouponRepository.findById(productOrderRequestDto.getIssuanceCouponId()).orElseThrow(() ->
                    new PaymentException("쿠폰 사용 오류, 결제 취소", ErrorCode.PAYMENT_ERROR, paymentCancelResponseDto));

            //이미 사용한 쿠폰을 사용했을때
            if(issuanceCoupon.isUse()){
                throw new PaymentException("사용 불가 쿠폰 사용, 결제 취소", ErrorCode.PAYMENT_ERROR, paymentCancelResponseDto);
            }

            issuanceCouponService.useUpdateIssuanceCoupon(issuanceCoupon.getId());
            productOrderRequestDto.setIssuanceCoupon(issuanceCoupon);
        }

        ProductOrder productOrder = productOrderRepository.save(productOrderRequestDto.toEntity());

        List<Goods> goodsList = goodsRepository.findGoodsIn(productOrderRequestDto.getGoodsIdList());
        List<Integer> itemCountList = productOrderRequestDto.getItemCountList();

        //상품 아이템과 갯수가 꼬이지않게 순서 다시 정렬 후 주문상품 아이템 저장
        List<Goods> newGoodsList = new ArrayList<>();
        for (int i = 0; i < goodsList.size(); i++) {
            for (Goods goods : goodsList) {
                if(Objects.equals(productOrderRequestDto.getGoodsIdList().get(i), goods.getId())){
                    newGoodsList.add(goods);
                }
            }
            ProductOrderItemDto productOrderItemDto = new ProductOrderItemDto();
            productOrderItemDto.setGoods(newGoodsList.get(i));
            productOrderItemDto.setItemCount(itemCountList.get(i));
            productOrderItemDto.setProductOrder(productOrder);

            ProductOrderItem productOrderItem = productOrderItemRepository.save(productOrderItemDto.toEntity());
            productOrder.addOrderItem(productOrderItem);
        } //for end

        return productOrder;
    }

//    public List<ProductOrderResponseDto> getProductOrderList(Long userId) {
//        List<ProductOrder> productOrderList = productOrderRepository.findAllFetchBy(userId);
//        List<ProductOrderResponseDto> productOrderResponseDtoList = new ArrayList<>();
//        for (ProductOrder productOrder : productOrderList) {
//            productOrderResponseDtoList.add(ProductOrderResponseDto.of(productOrder));
//        }
//        return productOrderResponseDtoList;
//    }

    //유저의 주문목록 가져오기
    public List<ProductOrderItemDto> getProductOrderItemList(Long userId) {
        List<ProductOrderItem> productOrderItemList = productOrderItemRepository.findAllByUser(userId);
        List<ProductOrderItemDto> productOrderItemDtoList = new ArrayList<>();
        for (ProductOrderItem productOrderItem : productOrderItemList) {
            productOrderItemDtoList.add(ProductOrderItemDto.of(productOrderItem));
        }
        return productOrderItemDtoList;
    }

    //주문상품 하나 가져오기
    public ProductOrderResponseDto getProductOrder(Long productOrderId) {
        ProductOrder productOrder = productOrderRepository.findAllFetchByUserAndId(productOrderId);

        return ProductOrderResponseDto.of(productOrder);
    }

    //결제 된 금액이 실제 결제해야 할 금액과 맞는지 체크
    @Transactional
    public boolean paymentCheck(String access_token, ProductOrderRequestDto productOrderRequestDto) throws IOException {

        int realAmount = 0;
        int couponDiscountAmount = 0;
        Long issuanceCouponId = productOrderRequestDto.getIssuanceCouponId();

        //쿠폰을 사용 하였다면 나중에 비교하기 위해서 쿠폰을 조회 후 할인금액을 가져옴
        if(!Objects.isNull(issuanceCouponId)){

            PaymentCancelResponseDto paymentCancelResponseDto =
                    new PaymentCancelResponseDto(productOrderRequestDto.getImpUid(), productOrderRequestDto.getPrice());

            IssuanceCoupon issuanceCoupon = issuanceCouponRepository.findById(issuanceCouponId).orElseThrow(() ->
                    new PaymentException("쿠폰 조회 에러, 결제 취소", ErrorCode.PAYMENT_ERROR, paymentCancelResponseDto));

            couponDiscountAmount = issuanceCoupon.getCoupon().getPrice();
        }

        //실제 결제 된 금액 가져오기
        int paymentAmount = iamportService.paymentInfo(productOrderRequestDto.getImpUid(), access_token);

        List<Goods> goodsList = goodsRepository.findGoodsIn(productOrderRequestDto.getGoodsIdList());
        List<Integer> itemCountList = productOrderRequestDto.getItemCountList();

        //goodsIdList가 repository에서 goods객체로 될때 순서가 꼬이는 걸 다시 정렬해서 저장
        //순서가 꼬이면 밑에 계산식이 안맞아서 예외발생
        List<Goods> newGoodsList = new ArrayList<>();
        for(int i = 0 ; i < goodsList.size(); i ++){
            for (Goods goods : goodsList) {
                if(Objects.equals(productOrderRequestDto.getGoodsIdList().get(i), goods.getId())){
                    newGoodsList.add(goods);
                }
            }

            realAmount += newGoodsList.get(i).getPrice() * itemCountList.get(i);
        }

        //결제된 금액이랑 차이가 없으면 트루 반환
        return paymentAmount == realAmount - couponDiscountAmount;
    }

    //관리자 페이지에서 주문목록 전체 확인
    public Page<ProductOrderResponseDto> getProductOrderAllList(PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageble(Sort.by("id").descending());
        Page<ProductOrder> productOrderPage = productOrderRepository.findAllFetchPageBy(pageable);
        return productOrderPage.map(ProductOrderResponseDto::of);
    }

    //배송상태 변경
    @Transactional
    public void productOrderStatusUpdate(Long id, final ProductOrderRequestDto p) {
        productOrderRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 주문입니다."));

        String orderStatus = Objects.requireNonNull(OrderStatus.nameOf(p.getOrderStatus())).getName();
        productOrderRepository.updateByStatus(orderStatus, id);
    }
}

