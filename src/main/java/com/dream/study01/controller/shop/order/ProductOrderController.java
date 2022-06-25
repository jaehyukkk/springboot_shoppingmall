package com.dream.study01.controller.shop.order;

import com.dream.study01.aop.UserRights;
import com.dream.study01.domain.entity.shop.order.ProductOrder;
import com.dream.study01.dto.PageRequestDto;
import com.dream.study01.dto.shop.order.PaymentCancelResponseDto;
import com.dream.study01.dto.shop.order.ProductOrderItemDto;
import com.dream.study01.dto.shop.order.ProductOrderRequestDto;
import com.dream.study01.dto.shop.order.ProductOrderResponseDto;
import com.dream.study01.error.ErrorCode;
import com.dream.study01.error.ErrorResponse;
import com.dream.study01.error.ForbiddenException;
import com.dream.study01.error.PaymentException;
import com.dream.study01.service.shop.iamport.IamportService;
import com.dream.study01.service.shop.order.ProductOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ProductOrderController {

    private final IamportService iamportService;
    private final ProductOrderService productOrderService;

    @Transactional
    @UserRights("ROLE_USER")
    @PostMapping("/api/v1/product-order")
    public ResponseEntity<Object> createOrder(ProductOrderRequestDto productOrderRequestDto, Principal principal) throws IOException{
        Long getUserId = (long) Integer.parseInt(principal.getName());

        PaymentCancelResponseDto paymentCancelResponseDto =
                new PaymentCancelResponseDto(productOrderRequestDto.getImpUid(), productOrderRequestDto.getPrice());

        String token = iamportService.getToken();
        if(!productOrderService.paymentCheck(token, productOrderRequestDto)){
            throw new PaymentException("결제금액 오류, 결제 취소", ErrorCode.PAYMENT_ERROR, paymentCancelResponseDto);
        }

        ProductOrder productOrder = productOrderService.createOrder(productOrderRequestDto, getUserId);
        return new ResponseEntity<>(productOrder, HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/product-order-item")
    @UserRights("ROLE_USER")
    public ResponseEntity<List<ProductOrderItemDto>> getProductOrderItemList(Principal principal) {
        Long getUserId = (long) Integer.parseInt(principal.getName());
        List<ProductOrderItemDto> productOrderItemDtoList = productOrderService.getProductOrderItemList(getUserId);
        return new ResponseEntity<>(productOrderItemDtoList, HttpStatus.OK);
    }

    @GetMapping("/api/v1/product-order/{productOrderId}")
    @UserRights("ROLE_USER")
    public ResponseEntity<ProductOrderResponseDto> getProductOrder(@PathVariable("productOrderId") Long productOrderId, Principal principal) {
        Long getUserId = (long) Integer.parseInt(principal.getName());
        ProductOrderResponseDto productOrderResponseDto = productOrderService.getProductOrder(productOrderId);
        //주문자가 아닐떄
        if (!Objects.equals(productOrderResponseDto.getUser().getId(), getUserId)) {
            throw new ForbiddenException("권한이 없습니다.", ErrorCode.FORBIDDEN);
        }
        return new ResponseEntity<>(productOrderResponseDto, HttpStatus.OK);
    }

    @GetMapping("/api/v1/product-order")
    @UserRights("ROLE_ADMIN")
    public ResponseEntity<Page<ProductOrderResponseDto>> getProductOrderAllList(PageRequestDto pageRequestDto) {
        Page<ProductOrderResponseDto> productOrderResponseDtoPage = productOrderService.getProductOrderAllList(pageRequestDto);
        return new ResponseEntity<>(productOrderResponseDtoPage, HttpStatus.OK);
    }

    @PatchMapping("/api/v1/product-order/{productOrderId}/status")
    @UserRights("ROLE_ADMIN")
    public ResponseEntity<?> productOrderStatusUpdate(@PathVariable("productOrderId") Long productOrderId, ProductOrderRequestDto productOrderRequestDto) {
        productOrderService.productOrderStatusUpdate(productOrderId, productOrderRequestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<Object> paymentException(PaymentException ex) throws IOException{
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode());

        PaymentCancelResponseDto paymentCancelResponseDto = ex.getPaymentCancelResponseDto();
        paymentCancelResponseDto.setAccess_token(iamportService.getToken());
        paymentCancelResponseDto.setReason(ex.getMessage());

        iamportService.paymentCancel(paymentCancelResponseDto);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }


}
