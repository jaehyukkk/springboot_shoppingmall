package com.dream.study01.domain.repository.shop.coupon;

import com.dream.study01.domain.entity.shop.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findAllByOrderByIdDesc();
}
