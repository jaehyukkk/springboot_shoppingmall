package com.dream.study01.service.shop.category;

import com.dream.study01.domain.entity.shop.category.MainCategory;
import com.dream.study01.domain.repository.shop.category.MainCategoryRepository;
import com.dream.study01.dto.shop.category.MainCategoryDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainCategoryService {

    private final MainCategoryRepository mainCategoryRepository;

    MainCategoryService(MainCategoryRepository mainCategoryRepository){
        this.mainCategoryRepository = mainCategoryRepository;
    }

    public List<MainCategoryDto> getMainCategoryList(){
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();
        List<MainCategoryDto> mainCategoryDtoList = new ArrayList<>();

        for(MainCategory mainCategory : mainCategoryList){
            MainCategoryDto mainCategoryDto = MainCategoryDto.builder()
                    .id(mainCategory.getId())
                    .subject(mainCategory.getSubject())
                    .createdDate(mainCategory.getCreatedDate())
                    .build();
            mainCategoryDtoList.add(mainCategoryDto);
        }

        return mainCategoryDtoList;
    }

}
