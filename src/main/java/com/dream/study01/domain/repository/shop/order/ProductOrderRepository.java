package com.dream.study01.domain.repository.shop.order;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.order.ProductOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    @Query(value = "select distinct p from ProductOrder p left join fetch p.productOrderItems i left join fetch p.user u left join fetch p.issuanceCoupon c where u.id = :userId order by p.id desc ")
    List<ProductOrder> findAllFetchBy(@Param("userId") Long userId);

    @Query(value = "select distinct p from ProductOrder p left join fetch p.productOrderItems i left join fetch p.user u left join fetch p.issuanceCoupon c where u.id = :userId and p.id = :productOrderId order by p.id desc ")
    ProductOrder findAllFetchByUserAndId(@Param("userId") Long userId, @Param("productOrderId") Long productOrderId);

}
