package com.dream.study01.domain.repository.shop.coupon;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.coupon.IssuanceCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuanceCouponRepository extends JpaRepository<IssuanceCoupon, Long> {
    List<IssuanceCoupon> findByUserOrderByIdDesc(User user);
    Page<IssuanceCoupon> findAll(Pageable pageable);
}
