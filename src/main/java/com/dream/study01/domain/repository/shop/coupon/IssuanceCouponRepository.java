package com.dream.study01.domain.repository.shop.coupon;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.coupon.IssuanceCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IssuanceCouponRepository extends JpaRepository<IssuanceCoupon, Long> {
    @Query("select c from IssuanceCoupon c where c.user = :user and c.isUse = false")
    List<IssuanceCoupon> findByUserOrderByIdDesc(@Param("user") User user);
    Page<IssuanceCoupon> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update IssuanceCoupon c set c.user = :user where c.id = :id")
    int userUpdateIssuanceCoupon(@Param("user") User user,
                                 @Param("id") Long issuanceCouponId);

    @Transactional
    @Modifying
    @Query("update IssuanceCoupon c set c.isUse = true where c.id = :id")
    void useUpdateIssuanceCoupon(@Param("id") Long issuanceCouponId);
}
