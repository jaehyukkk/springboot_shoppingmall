package com.dream.study01.controller.shop.coupon;

import com.dream.study01.domain.entity.shop.coupon.Coupon;
import com.dream.study01.dto.shop.coupon.CouponDto;
import com.dream.study01.service.shop.coupon.CouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService){
        this.couponService = couponService;
    }

    @PostMapping("/api/v1/coupon")
    public ResponseEntity<Coupon> createCoupon(CouponDto couponDto){
        Coupon coupon = couponService.createCoupon(couponDto);
        return new ResponseEntity<>(coupon, HttpStatus.OK);
    }

    @GetMapping("/api/v1/coupon")
    public ResponseEntity<List<CouponDto>> getCouponList(){
        return new ResponseEntity<>(couponService.getCouponList(), HttpStatus.OK);
    }
}
