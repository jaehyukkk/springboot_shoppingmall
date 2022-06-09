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

    public Coupon createCoupon(CouponDto couponDto){
        return couponRepository.save(couponDto.toEntity());
    }

    public List<CouponDto> getCouponList(){
        List<Coupon> couponList = couponRepository.findAllByOrderByIdDesc();
        List<CouponDto> couponDtoList = new ArrayList<>();
        for(Coupon coupon : couponList){
            couponDtoList.add(CouponDto.of(coupon));
        }
        return couponDtoList;
    }
}
