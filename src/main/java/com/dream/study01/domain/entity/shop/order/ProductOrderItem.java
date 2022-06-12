package com.dream.study01.domain.entity.shop.order;

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
public class ProductOrderItem {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @JoinColumn(name = "Goods_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Goods goods;

    @JsonIgnore
    @JoinColumn(name = "ProductOrder_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProductOrder productOrder;

    private Integer itemCount;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Builder
    public ProductOrderItem(Long id, Goods goods, ProductOrder productOrder, Integer itemCount) {
        this.id = id;
        this.goods = goods;
        this.productOrder = productOrder;
        this.itemCount = itemCount;
    }
}
