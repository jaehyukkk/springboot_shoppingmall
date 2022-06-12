package com.dream.study01.dto.shop.order;

import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.domain.entity.shop.order.ProductOrder;
import com.dream.study01.domain.entity.shop.order.ProductOrderItem;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderItemDto {

    private Long id;
    private Goods goods;
    private ProductOrder productOrder;
    private Integer itemCount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public ProductOrderItem toEntity() {
        return ProductOrderItem.builder()
                .id(id)
                .goods(goods)
                .productOrder(productOrder)
                .itemCount(itemCount)
                .build();
    }

    @Builder
    public ProductOrderItemDto(Goods goods, ProductOrder productOrder, Integer itemCount) {
        this.goods = goods;
        this.productOrder = productOrder;
        this.itemCount = itemCount;
    }

    public static ProductOrderItemDto of(ProductOrderItem productOrderItem){
        return new ProductOrderItemDto(
                productOrderItem.getId(),
                productOrderItem.getGoods(),
                productOrderItem.getProductOrder(),
                productOrderItem.getItemCount(),
                productOrderItem.getCreatedDate(),
                productOrderItem.getUpdatedDate());
    }

}
