package com.dream.study01.service.shop.category;

import com.dream.study01.domain.entity.shop.category.MainCategory;
import com.dream.study01.domain.entity.shop.category.SubCategory;
import com.dream.study01.domain.repository.shop.category.MainCategoryRepository;
import com.dream.study01.domain.repository.shop.category.SubCategoryRepository;
import com.dream.study01.dto.shop.category.SubCategoryDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final MainCategoryRepository mainCategoryRepository;

    SubCategoryService(SubCategoryRepository subCategoryRepository, MainCategoryRepository mainCategoryRepository){
        this.subCategoryRepository = subCategoryRepository;
        this.mainCategoryRepository = mainCategoryRepository;
    }

    public List<SubCategoryDto> getJoinSubCategoryList(Long mainCategoryId){

        MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryId).get();
        List<SubCategory> subCategoryList = subCategoryRepository.findAllByMainCategory(mainCategory);
        List<SubCategoryDto> subCategoryDtoList = new ArrayList<>();

        for(SubCategory subCategory : subCategoryList){
            SubCategoryDto subCategoryDto = SubCategoryDto.builder()
                    .id(subCategory.getId())
                    .subject(subCategory.getSubject())
                    .createdDate(subCategory.getCreatedDate())
                    .build();
            subCategoryDtoList.add(subCategoryDto);
        }
        return subCategoryDtoList;
    }

    public SubCategory createSubCategory(SubCategoryDto subCategoryDto){
        MainCategory mainCategory = mainCategoryRepository.findById(subCategoryDto.getMainCategoryId()).get();
        subCategoryDto.setMainCategory(mainCategory);
        return subCategoryRepository.save(subCategoryDto.toEntity());
    }
}
