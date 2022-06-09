package com.dream.study01.controller.shop.coupon;

import com.dream.study01.domain.entity.shop.coupon.IssuanceCoupon;
import com.dream.study01.dto.PageRequestDto;
import com.dream.study01.dto.shop.coupon.IssuanceCouponDto;
import com.dream.study01.service.shop.coupon.IssuanceCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IssuanceCouponController {

    private final IssuanceCouponService issuanceCouponService;

    @PostMapping("/api/v1/issuance-coupon")
    public ResponseEntity<List<IssuanceCoupon>> createIssuanceCoupon(@RequestParam("couponId") Long couponId, @RequestParam("issuesNum") Integer issuesNum) {
        List<IssuanceCoupon> issuanceCouponList = issuanceCouponService.createIssuanceCoupon(couponId, issuesNum);
        return new ResponseEntity<>(issuanceCouponList, HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/issuance-coupon")
    public ResponseEntity<Page<IssuanceCouponDto>> getIssuanceCouponList(PageRequestDto pageRequestDto){
        Page<IssuanceCouponDto> issuanceCouponDtoPage = issuanceCouponService.getIssuanceCouponList(pageRequestDto);
        return new ResponseEntity<>(issuanceCouponDtoPage, HttpStatus.OK);
    }
}
