package com.dream.study01.domain.entity.shop.order;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.coupon.IssuanceCoupon;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrder {

    @GeneratedValue
    @Id
    private Long id;

    @JoinColumn(name = "User_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String paymentUserName;

    private Integer price;

    private String impUid;

    private String memo;

    private String address;

    private Integer phone;

    @OneToMany(mappedBy = "productOrder", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<ProductOrderItem> productOrderItems = new ArrayList<>();

    public void addOrderItem(final ProductOrderItem productOrderItem){
        productOrderItems.add(productOrderItem);
        productOrderItem.setProductOrder(this);
    }

    @JoinColumn(name = "IssuanceCoupon_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private IssuanceCoupon issuanceCoupon;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Builder
    public ProductOrder(Long id, User user, String paymentUserName, Integer price, String impUid, String memo, String address, Integer phone, IssuanceCoupon issuanceCoupon) {
        this.id = id;
        this.user = user;
        this.paymentUserName = paymentUserName;
        this.price = price;
        this.impUid = impUid;
        this.memo = memo;
        this.address = address;
        this.phone = phone;
        this.issuanceCoupon = issuanceCoupon;
    }
}

