package com.dream.study01.service.shop.coupon;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.coupon.Coupon;
import com.dream.study01.domain.entity.shop.coupon.IssuanceCoupon;
import com.dream.study01.domain.repository.UserRepository;
import com.dream.study01.domain.repository.shop.coupon.CouponRepository;
import com.dream.study01.domain.repository.shop.coupon.IssuanceCouponRepository;
import com.dream.study01.dto.PageRequestDto;
import com.dream.study01.dto.shop.coupon.IssuanceCouponDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssuanceCouponService {
    private final IssuanceCouponRepository issuanceCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    public IssuanceCouponService(IssuanceCouponRepository issuanceCouponRepository, CouponRepository couponRepository, UserRepository userRepository) {
        this.issuanceCouponRepository = issuanceCouponRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    public List<IssuanceCoupon> createIssuanceCoupon(Long couponId, Integer issuesNum){
        Coupon coupon = couponRepository.getById(couponId);

        IssuanceCouponDto issuanceCouponDto = IssuanceCouponDto.builder()
                .coupon(coupon)
                .isUse(false)
                .build();

        List<IssuanceCoupon> issuanceCouponList = new ArrayList<>();

        for(int i = 0; i < issuesNum; i++){
            IssuanceCoupon issuanceCoupon = issuanceCouponRepository.save(issuanceCouponDto.toEntity());
            issuanceCouponList.add(issuanceCoupon);
        }
        return issuanceCouponList;
    }

    public Page<IssuanceCouponDto> getIssuanceCouponList(PageRequestDto pageRequestDto){
        Pageable pageable = pageRequestDto.getPageble(Sort.by("id").descending());
        Page<IssuanceCoupon> issuanceCouponPage = issuanceCouponRepository.findAll(pageable);
        return issuanceCouponPage.map(IssuanceCouponDto::of);
    }
}
