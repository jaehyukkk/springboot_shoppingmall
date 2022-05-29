package com.dream.study01.controller.shop.goods;

import com.dream.study01.domain.entity.shop.category.MainCategory;
import com.dream.study01.domain.entity.shop.category.SubCategory;
import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.domain.repository.shop.category.MainCategoryRepository;
import com.dream.study01.domain.repository.shop.category.SubCategoryRepository;
import com.dream.study01.dto.shop.file.FileDto;
import com.dream.study01.dto.shop.goods.GoodsDto;
import com.dream.study01.dto.shop.goods.GoodsRequestDto;
import com.dream.study01.service.shop.file.FileService;
import com.dream.study01.service.shop.goods.GoodsService;
import com.dream.study01.util.MD5Generator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
