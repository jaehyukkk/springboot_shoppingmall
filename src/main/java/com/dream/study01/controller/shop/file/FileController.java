package com.dream.study01.controller.shop.file;

import com.dream.study01.dto.shop.file.FileDto;
import com.dream.study01.service.shop.file.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @GetMapping("/api/v1/file/{id}")
    public ResponseEntity<Object> getFile(@PathVariable("id") Long id){
        try{
            FileDto fileDto = fileService.getFile(id);
            Path path = Paths.get(fileDto.getFilePath());
            Resource rs = new InputStreamResource(Files.newInputStream(path));
            return new ResponseEntity<>(rs,HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
