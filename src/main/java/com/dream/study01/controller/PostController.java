package com.dream.study01.controller;

import com.dream.study01.domain.entity.Post;
import com.dream.study01.dto.PostDto;
import com.dream.study01.service.PostService;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class PostController {

    private final PostService postService;

    PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping(value="/api/v1/post")
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto){

        Post post = postService.createPost(postDto);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
