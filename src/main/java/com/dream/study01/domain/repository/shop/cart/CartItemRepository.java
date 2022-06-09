package com.dream.study01.domain.repository.shop.cart;

import com.dream.study01.domain.entity.shop.cart.Cart;
import com.dream.study01.domain.entity.shop.cart.CartItem;
import com.dream.study01.domain.entity.shop.goods.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    public Page<CartItem> findByCart(Cart cart, Pageable pageable);

    Optional<CartItem> findById(Long aLong);

    CartItem findByCartAndGoods(Cart cart, Goods goods);

    @Query("select c from CartItem c where c.cart = :cart and c.id in :ids")
    List<CartItem> findByCartAndInId(@Param("cart") Cart cart,
                                     @Param("ids") List<Long> ids);

    @Transactional
    @Modifying
    @Query("UPDATE CartItem c SET c.itemCount = c.itemCount + :count WHERE c.id = :id")
    int updateItemCount(@Param("count") int cartItemCount,
                        @Param("id") Long cartItemId);

}
