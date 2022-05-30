package com.dream.study01.controller.shop.category;

import com.dream.study01.domain.entity.shop.category.SubCategory;
import com.dream.study01.dto.shop.category.MainCategoryDto;
import com.dream.study01.dto.shop.category.SubCategoryDto;
import com.dream.study01.service.shop.category.MainCategoryService;
import com.dream.study01.service.shop.category.SubCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final MainCategoryService mainCategoryService;
    private final SubCategoryService subCategoryService;

    CategoryController(MainCategoryService mainCategoryService, SubCategoryService subCategoryService){
        this.mainCategoryService = mainCategoryService;
        this.subCategoryService = subCategoryService;
    }

    @GetMapping("/api/v1/main-category")
    public ResponseEntity<Object> getMainCategory(){
        try{
            List<MainCategoryDto> mainCategoryList = mainCategoryService.getMainCategoryList();
            return new ResponseEntity<>(mainCategoryList, HttpStatus.OK);
        }catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/api/v1/sub-category/{mainCategoryId}")
    public ResponseEntity<Object> getJoinSubCategory(@PathVariable Long mainCategoryId){
        List<SubCategoryDto> subCategoryDtoList = subCategoryService.getJoinSubCategoryList(mainCategoryId);
        return new ResponseEntity<>(subCategoryDtoList,HttpStatus.OK);
    }

    @PostMapping("/api/v1/sub-category")
    public ResponseEntity<Object> createSubCategory(@RequestBody SubCategoryDto subCategoryDto){
        SubCategory subCategory = subCategoryService.createSubCategory(subCategoryDto);
        return new ResponseEntity<>(subCategory,HttpStatus.OK);
    }
}
