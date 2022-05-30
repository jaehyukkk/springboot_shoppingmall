package com.dream.study01.service.shop.goods;

import com.dream.study01.domain.entity.shop.category.MainCategory;
import com.dream.study01.domain.entity.shop.category.SubCategory;
import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.domain.repository.shop.category.MainCategoryRepository;
import com.dream.study01.domain.repository.shop.category.SubCategoryRepository;
import com.dream.study01.domain.repository.shop.goods.GoodsRepository;
import com.dream.study01.dto.shop.file.FileDto;
import com.dream.study01.dto.shop.goods.GoodsDto;
import com.dream.study01.dto.shop.goods.GoodsRequestDto;
import com.dream.study01.service.shop.file.FileService;
import com.dream.study01.service.shop.file.FileSetting;
import com.dream.study01.util.MD5Generator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final FileService fileService;

    GoodsService(GoodsRepository goodsRepository, MainCategoryRepository mainCategoryRepository,
                 SubCategoryRepository subCategoryRepository, FileService fileService){
        this.goodsRepository = goodsRepository;
        this.mainCategoryRepository = mainCategoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.fileService = fileService;
    }

    @Transactional
    public Goods createGoods(GoodsRequestDto goodsRequestDto, List<MultipartFile> multipartFiles) throws IOException, NoSuchAlgorithmException {
        Goods goods = categorySettingEntityTransformation(goodsRequestDto);

        for(MultipartFile multipartFile : multipartFiles){

            FileDto fileDto = new FileDto();
            FileSetting fileSetting = new FileSetting(multipartFile);

            fileDto.setFilename(fileSetting.getFilename());
            fileDto.setFilePath(fileSetting.getFilePath());
            fileDto.setOrigFilename(fileSetting.getOrigFilename());
            fileDto.setGoods(goods);

            fileService.createFile(fileDto);
        }
        return goodsRepository.save(goods);
    }

    @Transactional
    public void updateGoods(GoodsRequestDto goodsRequestDto, List<MultipartFile> multipartFiles) throws IOException, NoSuchAlgorithmException {
        Goods goods = categorySettingEntityTransformation(goodsRequestDto);
        Long getGoodsId = goodsRequestDto.getId();
        Goods getGoods = goodsRepository.findById(getGoodsId).get();

        LocalDateTime createdDate = getGoods.getCreatedDate();
        goodsRequestDto.setCreatedDate(createdDate);

        int i = 0;
        if(multipartFiles != null){
            for(MultipartFile multipartFile : multipartFiles){
                FileDto fileDto = new FileDto();

                FileSetting fileSetting = new FileSetting(multipartFile);

                fileDto.setFilename(fileSetting.getFilename());
                fileDto.setFilePath(fileSetting.getFilePath());
                fileDto.setOrigFilename(fileSetting.getOrigFilename());
                fileDto.setGoods(goods);

                if(!goodsRequestDto.getFileIds().isEmpty()){
                    fileDto.setId(goodsRequestDto.getFileIds().get(i));
                }
                goods.addFile(fileDto.toEntity());
                i++;
            }
        }
        goods.update(goodsRequestDto);
        goodsRepository.save(goods);
    }




    public List<GoodsDto> getGoodsList(){
        List<Goods> goodsList = goodsRepository.findAllFetch();
        List<GoodsDto> goodsDtoList = new ArrayList<>();
        for(Goods goods : goodsList){
            GoodsDto goodsDto = new GoodsDto(goods);
            goodsDtoList.add(goodsDto);
        }
        return goodsDtoList;
    }

    public GoodsDto getGoods(Long id){
        Goods goods = goodsRepository.findByIdFetch(id);
        GoodsDto goodsDto = new GoodsDto(goods);
        return goodsDto;
    }



    public List<GoodsDto> getMainCategoryGoodsList(Long mainCategoryId){
        List<Goods> goodsList = goodsRepository.findAllByMainCategory(mainCategoryFindById(mainCategoryId));
        List<GoodsDto> goodsDtoList = new ArrayList<>();
        for(Goods goods : goodsList){
            GoodsDto goodsDto = new GoodsDto(goods);
            goodsDtoList.add(goodsDto);
        }
        return goodsDtoList;

    }

    public List<GoodsDto> getSubCategoryGoodsList(Long mainCategoryId, Long subCategoryId, Pageable pageable){

        Page<Goods> goodsList = goodsRepository.findAllByMainCategoryAndSubCategory(
                mainCategoryFindById(mainCategoryId)
                ,subCategoryFindById(subCategoryId)
                ,pageable);

        List<GoodsDto> goodsDtoList = new ArrayList<>();
        for(Goods goods : goodsList){
            GoodsDto goodsDto = new GoodsDto(goods);
            goodsDtoList.add(goodsDto);
        }
        return goodsDtoList;
    }

    public MainCategory mainCategoryFindById(Long mainCategoryId){
        return mainCategoryRepository.findById(mainCategoryId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }

    public SubCategory subCategoryFindById(Long subCategoryId){
        return subCategoryRepository.findById(subCategoryId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }


    public Goods categorySettingEntityTransformation(GoodsRequestDto goodsRequestDto){
        Long mainCategoryId = goodsRequestDto.getMainCategoryId();
        Long subCategoryId = goodsRequestDto.getSubCategoryId();

        goodsRequestDto.setMainCategory(mainCategoryFindById(mainCategoryId));
        goodsRequestDto.setSubCategory(subCategoryFindById(subCategoryId));

        Goods goods = goodsRequestDto.toEntity();

        return goods;
    }
}
