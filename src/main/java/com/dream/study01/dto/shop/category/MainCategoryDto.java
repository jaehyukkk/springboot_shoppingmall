package com.dream.study01.dto.shop.category;

import com.dream.study01.domain.entity.shop.category.MainCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MainCategoryDto {

    private Long id;
    private String subject;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public MainCategory toEntity(){

        return MainCategory.builder()
                .id(id)
                .subject(subject)
                .build();
    }

    @Builder
    public MainCategoryDto(Long id, String subject, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.subject = subject;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
