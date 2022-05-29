package com.dream.study01.service.shop.file;

import com.dream.study01.domain.entity.shop.file.File;
import com.dream.study01.domain.entity.shop.goods.Goods;
import com.dream.study01.domain.repository.shop.file.FileRepository;
import com.dream.study01.dto.shop.file.FileDto;
import com.dream.study01.util.MD5Generator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;

    FileService(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    public void createFile(FileDto fileDto) {
        fileRepository.save(fileDto.toEntity());
    }


    @Transactional
    public FileDto getFile(Long id){
        File file = fileRepository.findById(id).get();
        FileDto fileDto = new FileDto(file);
        return fileDto;
    }
}
