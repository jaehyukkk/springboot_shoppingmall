package com.dream.study01.domain.entity.shop.cart;

import com.dream.study01.domain.entity.shop.goods.Goods;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="goods_id")
    private Goods goods;

    private int itemCount;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Builder
    public CartItem(Long id, Cart cart, Goods goods, int itemCount, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.cart = cart;
        this.goods = goods;
        this.itemCount = itemCount;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
