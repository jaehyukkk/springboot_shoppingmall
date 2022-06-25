package com.dream.study01.domain.repository.shop.order;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.order.ProductOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    @Query(value = "select distinct p from ProductOrder p left join fetch p.productOrderItems i left join fetch p.user u left join fetch p.issuanceCoupon c where u.id = :userId order by p.id desc ")
    List<ProductOrder> findAllFetchBy(@Param("userId") Long userId);

    @Query(value = "select distinct p from ProductOrder p left join fetch p.productOrderItems i left join fetch p.issuanceCoupon c where p.id = :productOrderId order by p.id desc ")
    ProductOrder findAllFetchByUserAndId(@Param("productOrderId") Long productOrderId);

    @EntityGraph(
            attributePaths = {"productOrderItems", "user", "issuanceCoupon"}
    )
    Page<ProductOrder> findAllFetchPageBy(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update ProductOrder p set p.orderStatus = :orderStatus where p.id = :productOrderId")
    void updateByStatus(@Param("orderStatus") String orderStatus, @Param("productOrderId") Long productOrderId);
}
