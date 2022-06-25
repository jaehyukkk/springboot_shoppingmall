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
import org.springframework.transaction.annotation.Transactional;

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

    //쿠폰 원하는 매수 발행해주기
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

    //발행 된 쿠폰 리스트
    public Page<IssuanceCouponDto> getIssuanceCouponList(PageRequestDto pageRequestDto){
        Pageable pageable = pageRequestDto.getPageble(Sort.by("id").descending());
        Page<IssuanceCoupon> issuanceCouponPage = issuanceCouponRepository.findAll(pageable);
        return issuanceCouponPage.map(IssuanceCouponDto::of);
    }

    //유저에게 쿠폰 발행
    @Transactional
    public int userUpdateIssuanceCoupon(String email, Long issuanceCouponId) {
        User user = userRepository.findByEmail(email).orElseThrow(()->
        new IllegalArgumentException("유저 조회 결과가 없습니다."));

        return issuanceCouponRepository.userUpdateIssuanceCoupon(user, issuanceCouponId);
    }

    //자신에게 발행된 쿠폰리스트
    public List<IssuanceCouponDto> getUserCouponList(Long userId){

        User user = userRepository.findById(userId).orElseThrow(()->
            new IllegalArgumentException("유저 조회 결과가 없습니다."));

        List<IssuanceCoupon> issuanceCouponList = issuanceCouponRepository.findByUserOrderByIdDesc(user);
        List<IssuanceCouponDto> issuanceCouponDtoList = new ArrayList<>();

        for(IssuanceCoupon issuanceCoupon : issuanceCouponList){
            issuanceCouponDtoList.add(IssuanceCouponDto.of(issuanceCoupon));
        }

        return issuanceCouponDtoList;
    }

    public void useUpdateIssuanceCoupon(Long id) {
        issuanceCouponRepository.useUpdateIssuanceCoupon(id);
    }


}
