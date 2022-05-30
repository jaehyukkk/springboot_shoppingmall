package com.dream.study01.controller.shop.goods;

import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.dto.shop.goods.GoodsDto;
import com.dream.study01.dto.shop.goods.GoodsRequestDto;
import com.dream.study01.service.shop.file.FileService;
import com.dream.study01.service.shop.goods.GoodsService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
public class GoodsController {

   private final GoodsService goodsService;
   private final FileService fileService;

   GoodsController(GoodsService goodsService, FileService fileService){
       this.goodsService = goodsService;
       this.fileService = fileService;
   }

    @PostMapping("/api/v1/goods")
    public ResponseEntity<Object> createGoods(@RequestParam(value= "file", required = false)List<MultipartFile> multipartFiles, GoodsRequestDto goodsRequestDto){
        try{
            Goods goods = goodsService.createGoods(goodsRequestDto,multipartFiles);
            return new ResponseEntity<>(goods, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/v1/goods/{id}")
    public ResponseEntity<Object> updateGoods(@RequestParam(value= "file", required = false)List<MultipartFile> multipartFiles, GoodsRequestDto goodsRequestDto){
       try{
           goodsService.updateGoods(goodsRequestDto, multipartFiles);
           return new ResponseEntity<>(HttpStatus.OK);
       } catch (Exception e){
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

    }

    @GetMapping("/api/v1/main-category/{mainCategoryId}/goods")
    public ResponseEntity<Object> getMainCategoryGoodsList(@PathVariable("mainCategoryId") Long mainCategoryId){
       try{
           List<GoodsDto> goodsDtoList = goodsService.getMainCategoryGoodsList(mainCategoryId);
           return new ResponseEntity<>(goodsDtoList,HttpStatus.OK);
       } catch (IllegalArgumentException ex){
           return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
       }

    }

    @GetMapping("/api/v1/main-category/{mainCategoryId}/sub-category/{subCategoryId}/goods")
    public ResponseEntity<Object> getSubCategoryGoodsList(@PathVariable Long mainCategoryId, @PathVariable Long subCategoryId, @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
       try{
           List<GoodsDto> goodsDtoList = goodsService.getSubCategoryGoodsList(mainCategoryId,subCategoryId,pageable);
           return new ResponseEntity<>(goodsDtoList, HttpStatus.OK);
       } catch (Exception ex){
           return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
       }

    }

    @GetMapping("/api/v1/goods")
    public ResponseEntity<List<GoodsDto>> getGoodsList(){
        List<GoodsDto> goodsDtoList = goodsService.getGoodsList();
        return new ResponseEntity<>(goodsDtoList,HttpStatus.OK);
    }

    @GetMapping("/api/v1/goods/{id}")
    public ResponseEntity<GoodsDto> getGoods(@PathVariable("id") Long id){
       GoodsDto goodsDto = goodsService.getGoods(id);
       return new ResponseEntity<>(goodsDto,HttpStatus.OK);
    }

}
