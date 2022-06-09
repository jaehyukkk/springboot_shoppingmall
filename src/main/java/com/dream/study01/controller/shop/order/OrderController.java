package com.dream.study01.controller.shop.order;

import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.domain.repository.shop.goods.GoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final GoodsRepository goodsRepository;

//    @PostMapping("/api/v1/order")
//    public ResponseEntity<Map<Long, Integer>> newOrder(@RequestParam("goodsIdList") List<Long> goodsIdList, @RequestParam("itemCntList") List<Integer> itemCntList){
//        Map<Long, Integer> goodsMap = new HashMap<>();
//        for(int i = 0 ; i < goodsIdList.size(); i ++){
//            goodsMap.put(goodsIdList.get(i), itemCntList.get(i));
//        }
//        return new ResponseEntity<>(goodsMap, HttpStatus.OK);
//    }

}
