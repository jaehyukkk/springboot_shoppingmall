package com.dream.study01.dto.shop.goods;

import com.dream.study01.domain.entity.shop.category.MainCategory;
import com.dream.study01.domain.entity.shop.category.SubCategory;
import com.dream.study01.domain.entity.shop.file.File;
import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.dto.shop.file.FileDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class GoodsDto {

    private Long id;
    private String title;
    private String description;
    private Integer price;
    private Long mainCategoryId;
    private Long subCategoryId;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private List<FileDto> files;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime updateDate;
    private Goods goods;


    public GoodsDto(Goods goods) {
        this.id = goods.getId();
        this.title = goods.getTitle();
        this.description = goods.getDescription();
        this.price = goods.getPrice();
        this.mainCategory = goods.getMainCategory();
        this.subCategory = goods.getSubCategory();
        this.files = goods.getFiles().stream().map(FileDto::new).collect(Collectors.toList());
        this.createdDate = goods.getCreatedDate();
        this.updateDate = goods.getUpdatedDate();
    }

}
