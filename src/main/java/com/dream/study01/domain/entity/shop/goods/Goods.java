package com.dream.study01.domain.entity.shop.goods;

import com.dream.study01.domain.entity.shop.category.MainCategory;
import com.dream.study01.domain.entity.shop.category.SubCategory;
import com.dream.study01.domain.entity.shop.file.File;
import com.dream.study01.dto.shop.goods.GoodsRequestDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods {

    @GeneratedValue
    @Id
    private Long id;

    private String title;
    private String description;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MainCategory_id")
    private MainCategory mainCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubCategory_id")
    private SubCategory subCategory;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "goods", fetch = FetchType.LAZY,cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<File> files = new ArrayList<>();


    public void addFile(final File file){
        files.add(file);
        file.setGoods(this);
    }
    public void removeFile(final File file){
        files.remove(file);
        file.setGoods(null);
    }

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    public void update(final GoodsRequestDto goodsRequestDto){
        this.id = goodsRequestDto.getId();
        this.title = goodsRequestDto.getTitle();
        this.price = goodsRequestDto.getPrice();
        this.mainCategory = goodsRequestDto.getMainCategory();
        this.subCategory = goodsRequestDto.getSubCategory();
        this.createdDate = goodsRequestDto.getCreatedDate();
    }

    @Builder
    public Goods(Long id, String title, String description, Integer price, MainCategory mainCategory, SubCategory subCategory) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }
}
