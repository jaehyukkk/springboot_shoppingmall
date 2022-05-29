package com.dream.study01.dto.shop.goods;

import com.dream.study01.domain.entity.shop.category.MainCategory;
import com.dream.study01.domain.entity.shop.category.SubCategory;
import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.dto.shop.file.FileDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GoodsRequestDto {

    private Long id;
    private String title;
    private String description;
    private Integer price;
    private Long mainCategoryId;
    private Long subCategoryId;
    private List<Long> fileIds;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private List<FileDto> files;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    public Goods toEntity(){
        Goods goods = Goods.builder()
                .id(id)
                .title(title)
                .description(description)
                .price(price)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .build();
        return goods;
    }
}
