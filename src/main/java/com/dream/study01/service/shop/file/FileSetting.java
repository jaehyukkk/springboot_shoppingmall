package com.dream.study01.service.shop.file;

import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.util.MD5Generator;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Getter
public class FileSetting {

    private String origFilename;
    private String filename;
    private String filePath;

    public FileSetting(MultipartFile multipartFile) throws IOException, NoSuchAlgorithmException {

        String origFilename = multipartFile.getOriginalFilename();
        String ext = origFilename.substring(origFilename.lastIndexOf(".") + 1);
        String filename = new MD5Generator(origFilename).toString() + "." + ext;
        String savePath = System.getProperty("user.dir") + "/files";
        if (!new java.io.File(savePath).exists()) {
            try {
                new java.io.File(savePath).mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String filePath = savePath + "/" + filename;
        multipartFile.transferTo(new java.io.File(filePath));

        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }

}
