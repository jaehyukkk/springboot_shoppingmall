package com.dream.study01.service.shop.goods;

import com.dream.study01.domain.entity.shop.category.MainCategory;
import com.dream.study01.domain.entity.shop.category.SubCategory;
import com.dream.study01.domain.entity.shop.file.File;
import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.domain.repository.shop.category.MainCategoryRepository;
import com.dream.study01.domain.repository.shop.category.SubCategoryRepository;
import com.dream.study01.domain.repository.shop.goods.GoodsRepository;
import com.dream.study01.dto.PageRequestDto;
import com.dream.study01.dto.PageResultDto;
import com.dream.study01.dto.shop.file.FileDto;
import com.dream.study01.dto.shop.goods.GoodsDto;
import com.dream.study01.dto.shop.goods.GoodsRequestDto;
import com.dream.study01.service.shop.file.FileService;
import com.dream.study01.service.shop.file.FileSetting;
import com.dream.study01.util.MD5Generator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        Goods goods = goodsRepository.save(categorySetting(goodsRequestDto).toEntity());

        for(MultipartFile multipartFile : multipartFiles){

            FileDto fileDto = new FileDto();
            FileSetting fileSetting = new FileSetting(multipartFile);

            fileDto.setFilename(fileSetting.getFilename());
            fileDto.setFilePath(fileSetting.getFilePath());
            fileDto.setOrigFilename(fileSetting.getOrigFilename());
            fileDto.setGoods(goods);

            File file = fileService.createFile(fileDto);
            goods.addFile(file);
        }
        return goods;
    }

    @Transactional
    public void updateGoods(GoodsRequestDto goodsRequestDto, List<MultipartFile> multipartFiles) throws IOException, NoSuchAlgorithmException {
        Goods goods = categorySetting(goodsRequestDto).toEntity();
        Goods getGoods = goodsRepository.findById(goodsRequestDto.getId()).orElseThrow(()->
                new IllegalArgumentException("해당아이디가 존재하지 않습니다."));
        goodsRequestDto.setCreatedDate(getGoods.getCreatedDate());

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
                    if(goodsRequestDto.getFileIds().get(i) != null){
                        fileDto.setId(goodsRequestDto.getFileIds().get(i));
                    }
                }
//                System.out.println("=======================================");
//                System.out.println(goodsRequestDto.getFileIds().get(i));
//                System.out.println("=======================================");
                goods.addFile(fileDto.toEntity());
                i++;
            }
        }
        goods.update(goodsRequestDto);
        goodsRepository.save(goods);
    }




    public Page<GoodsDto> getGoodsList(PageRequestDto pageRequestDto){
        Pageable pageable = pageRequestDto.getPageble(Sort.by("id").descending());
        Page<Goods> goodsList = goodsRepository.findALlFetchBy(pageable);
        return goodsList.map(GoodsDto::new);
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

    public Page<GoodsDto> getSubCategoryGoodsList(Long mainCategoryId, Long subCategoryId, PageRequestDto requestDto){

        Pageable pageable = requestDto.getPageble(Sort.by("id").descending());

        Page<Goods> goodsList = goodsRepository.findAllByMainCategoryAndSubCategory(
                mainCategoryFindById(mainCategoryId)
                ,subCategoryFindById(subCategoryId)
                ,pageable);

        return goodsList.map(GoodsDto::new);
    }





    @Transactional
    public void removeGoods(Long id){
       Goods goods = goodsRepository.findByIdFetch(id);
       List<FileDto> fileList = goods.getFiles().stream().map(FileDto::new).collect(Collectors.toList());
       for(FileDto fileDto : fileList){
//           log.info("===================================");
//           log.info("파일이름 : " + fileDto.getOrigFilename());
//           log.info("===================================");
           goods.removeFile(fileDto.toEntity());
       }
        goodsRepository.deleteById(id);
    }

    public MainCategory mainCategoryFindById(Long mainCategoryId){
        return mainCategoryRepository.findById(mainCategoryId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }

    public SubCategory subCategoryFindById(Long subCategoryId){
        return subCategoryRepository.findById(subCategoryId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }


    GoodsRequestDto categorySetting(GoodsRequestDto goodsRequestDto){
        Long mainCategoryId = goodsRequestDto.getMainCategoryId();
        Long subCategoryId = goodsRequestDto.getSubCategoryId();

        goodsRequestDto.setMainCategory(mainCategoryFindById(mainCategoryId));
        goodsRequestDto.setSubCategory(subCategoryFindById(subCategoryId));

        return goodsRequestDto;
    }

}
