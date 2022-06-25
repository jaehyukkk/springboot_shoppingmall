package com.dream.study01.controller;

import com.dream.study01.domain.entity.Post;
import com.dream.study01.dto.PostDto;
import com.dream.study01.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class PostController {

    private final PostService postService;

    PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping(value="/api/v1/post")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto){

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.OK);
    }

    @GetMapping(value = "/api/v1/post")
    public ResponseEntity<List<PostDto>> postList(){
        List<PostDto> postDtoList = postService.getPostList();

        return ResponseEntity.ok().body(postDtoList);
    }

    @GetMapping(value = "/api/v1/post/{id}")
    public ResponseEntity<PostDto> getPost(@RequestBody @PathVariable Long id){
        PostDto postDto = postService.getPost(id);
        return ResponseEntity.ok().body(postDto);
    }

    @PutMapping(value = "/api/v1/post/{id}")
    public ResponseEntity<Integer> updatePost(@RequestBody PostDto postDto){
        Integer post = postService.setPost(postDto);
        return ResponseEntity.ok().body(post);
    }
    @DeleteMapping(value = "api/v1/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
            postService.deletePost(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
