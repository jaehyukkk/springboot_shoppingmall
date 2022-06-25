package com.dream.study01.controller.shop.coupon;

import com.dream.study01.domain.entity.shop.coupon.Coupon;
import com.dream.study01.dto.shop.coupon.CouponDto;
import com.dream.study01.error.ErrorCode;
import com.dream.study01.error.ErrorResponse;
import com.dream.study01.service.shop.coupon.CouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService){
        this.couponService = couponService;
    }

    @PostMapping("/api/v1/coupon")
    public ResponseEntity<CouponDto> createCoupon(@Valid CouponDto couponDto){
        return ResponseEntity.ok(couponService.createCoupon(couponDto));
    }

    @GetMapping("/api/v1/coupon")
    public ResponseEntity<List<CouponDto>> getCouponList(){
        return new ResponseEntity<>(couponService.getCouponList(), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validException(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), ErrorCode.VALID_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
