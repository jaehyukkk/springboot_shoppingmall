package com.dream.study01.service.shop.coupon;

import com.dream.study01.domain.entity.shop.coupon.Coupon;
import com.dream.study01.domain.repository.shop.coupon.CouponRepository;
import com.dream.study01.dto.shop.coupon.CouponDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    //쿠폰생성
    public CouponDto createCoupon(CouponDto couponDto){
        Coupon coupon = couponRepository.save(couponDto.toEntity());
        return CouponDto.of(coupon);
    }

    //쿠폰리스트 가져오기
    public List<CouponDto> getCouponList(){
        List<Coupon> couponList = couponRepository.findAllByOrderByIdDesc();
        List<CouponDto> couponDtoList = new ArrayList<>();
        for(Coupon coupon : couponList){
            couponDtoList.add(CouponDto.of(coupon));
        }
        return couponDtoList;
    }
}
