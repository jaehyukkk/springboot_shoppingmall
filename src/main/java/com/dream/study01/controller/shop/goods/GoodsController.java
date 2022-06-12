package com.dream.study01.controller.shop.goods;

import com.dream.study01.aop.AdminRights;
import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.dto.PageRequestDto;
import com.dream.study01.dto.shop.goods.GoodsDto;
import com.dream.study01.dto.shop.goods.GoodsRequestDto;
import com.dream.study01.service.shop.goods.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
public class GoodsController {

   private final GoodsService goodsService;

   GoodsController(GoodsService goodsService){
       this.goodsService = goodsService;
   }

    @PostMapping("/api/v1/goods")
//    @AdminRights
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
//    @AdminRights
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
    public ResponseEntity<Object> getMainCategoryGoodsList(@PathVariable("mainCategoryId") Long mainCategoryId, Principal principal){
       try{
           List<GoodsDto> goodsDtoList = goodsService.getMainCategoryGoodsList(mainCategoryId);
           return new ResponseEntity<>(goodsDtoList,HttpStatus.OK);
       } catch (IllegalArgumentException ex){
           return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
       }

    }

    @GetMapping("/api/v1/main-category/{mainCategoryId}/sub-category/{subCategoryId}/goods")
    public ResponseEntity<Object> getSubCategoryGoodsList(@PathVariable Long mainCategoryId, @PathVariable Long subCategoryId, PageRequestDto pageRequestDto){
       try{
           Page<GoodsDto> goodsDtoPage = goodsService.getSubCategoryGoodsList(mainCategoryId,subCategoryId,pageRequestDto);
           return new ResponseEntity<>(goodsDtoPage, HttpStatus.OK);
       } catch (Exception ex){
           return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
       }

    }

    @GetMapping("/api/v1/goods")
    public ResponseEntity<Page<GoodsDto>> getGoodsList(PageRequestDto pageRequestDto){
        Page<GoodsDto> goodsDtoList = goodsService.getGoodsList(pageRequestDto);
        return new ResponseEntity<>(goodsDtoList,HttpStatus.OK);
    }

    @GetMapping("/api/v1/goods/{id}")
    public ResponseEntity<GoodsDto> getGoods(@PathVariable("id") Long id){
       GoodsDto goodsDto = goodsService.getGoods(id);
       return new ResponseEntity<>(goodsDto,HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/goods/{id}")
//    @AdminRights
    public ResponseEntity<?> removeGoods(@PathVariable("id") Long id){
       try{
           goodsService.removeGoods(id);
           return new ResponseEntity<>(HttpStatus.OK);
       } catch (Exception ex){
           ex.printStackTrace();
           return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
       }

    }
}
