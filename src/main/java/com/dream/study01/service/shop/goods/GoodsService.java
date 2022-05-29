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
import com.dream.study01.util.MD5Generator;
import org.springframework.stereotype.Service;
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

    public Goods createGoods(GoodsRequestDto goodsRequestDto, List<MultipartFile> multipartFiles) throws IOException, NoSuchAlgorithmException {
        Goods goods = categorySettingEntityTransformation(goodsRequestDto);

        for(MultipartFile multipartFile : multipartFiles){
            String origFilename = multipartFile.getOriginalFilename();
            String ext = origFilename.substring(origFilename.lastIndexOf(".") + 1);
            String filename = new MD5Generator(origFilename).toString()+"."+ext;
            String savePath = System.getProperty("user.dir") + "/files";
            if(!new java.io.File(savePath).exists()){
                try{
                    new java.io.File(savePath).mkdir();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            String filePath = savePath + "/" + filename;
            multipartFile.transferTo(new java.io.File(filePath));

            FileDto fileDto = new FileDto();
            fileDto.setOrigFilename(origFilename);
            fileDto.setFilename(filename);
            fileDto.setFilePath(filePath);
            fileDto.setGoods(goods);
//            goods.addFile(fileDto.toEntity());
            fileService.createFile(fileDto);
        }

        return goodsRepository.save(goods);
    }

    public void updateGoods(GoodsRequestDto goodsRequestDto, List<MultipartFile> multipartFiles) throws IOException, NoSuchAlgorithmException {
        Goods goods = categorySettingEntityTransformation(goodsRequestDto);
        Long getGoodsId = goodsRequestDto.getId();
        Goods getGoods = goodsRepository.findById(getGoodsId).get();

        LocalDateTime createdDate = getGoods.getCreatedDate();
        goodsRequestDto.setCreatedDate(createdDate);
        int i = 0;
        if(multipartFiles != null){
            for(MultipartFile multipartFile : multipartFiles){
                String origFilename = multipartFile.getOriginalFilename();
                String ext = origFilename.substring(origFilename.lastIndexOf(".") + 1);
                String filename = new MD5Generator(origFilename).toString()+"."+ext;
                String savePath = System.getProperty("user.dir") + "/files";
                if(!new java.io.File(savePath).exists()){
                    try{
                        new java.io.File(savePath).mkdir();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                String filePath = savePath + "/" + filename;
                multipartFile.transferTo(new java.io.File(filePath));

                FileDto fileDto = new FileDto();
                if(!goodsRequestDto.getFileIds().isEmpty()){
                    fileDto.setId(goodsRequestDto.getFileIds().get(i));
                }
                fileDto.setOrigFilename(origFilename);
                fileDto.setFilename(filename);
                fileDto.setFilePath(filePath);
                fileDto.setGoods(goods);
                goods.addFile(fileDto.toEntity());
//                fileService.createFile(fileDto);
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

    public Goods categorySettingEntityTransformation(GoodsRequestDto goodsRequestDto){
        Long mainCategoryId = goodsRequestDto.getMainCategoryId();
        Long subCategoryId = goodsRequestDto.getSubCategoryId();

        MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryId).get();
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).get();

        goodsRequestDto.setMainCategory(mainCategory);
        goodsRequestDto.setSubCategory(subCategory);

        Goods goods = goodsRequestDto.toEntity();

        return goods;
    }
}
