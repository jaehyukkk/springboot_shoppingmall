package com.dream.study01.domain.repository.shop.order;

import com.dream.study01.domain.entity.shop.order.ProductOrder;
import com.dream.study01.domain.entity.shop.order.ProductOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderItemRepository extends JpaRepository<ProductOrderItem, Long> {

    @Query(value = "select i from ProductOrderItem i left join i.productOrder p left join p.user u where u.id = :userId")
    List<ProductOrderItem> findAllByUser(@Param("userId") Long userId);
}
