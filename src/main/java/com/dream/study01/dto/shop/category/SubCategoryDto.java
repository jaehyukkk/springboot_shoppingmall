package com.dream.study01.dto.shop.category;

import com.dream.study01.domain.entity.shop.category.MainCategory;
import com.dream.study01.domain.entity.shop.category.SubCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SubCategoryDto {

    private Long id;
    private String subject;
    private MainCategory mainCategory;
    private Long mainCategoryId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public SubCategory toEntity(){
        return SubCategory.builder()
                .id(id)
                .subject(subject)
                .mainCategory(mainCategory)
                .build();
    }

    @Builder
    public SubCategoryDto(Long id, String subject, MainCategory mainCategory, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.subject = subject;
        this.mainCategory = mainCategory;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
