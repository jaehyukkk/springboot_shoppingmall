package com.dream.study01.dto.shop.file;

import com.dream.study01.domain.entity.shop.file.File;
import com.dream.study01.domain.entity.shop.goods.Goods;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class FileDto {

    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;
    private String mainImgYn;
    private Goods goods;


    public File toEntity(){
        File file = File.builder()
                .id(id)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .mainImgYn(mainImgYn)
                .goods(goods)
                .build();
        return file;
    }


    public FileDto(File file) {
        this.id = file.getId();
        this.origFilename = file.getOrigFilename();
        this.filename = file.getFilename();
        this.filePath = file.getFilePath();
        this.mainImgYn = file.getMainImgYn();
        this.goods = file.getGoods();
    }
}
