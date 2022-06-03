package com.dream.study01.controller;

import com.dream.study01.domain.entity.Post;
import com.dream.study01.dto.PostDto;
import com.dream.study01.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping(value="/api/v1/post")
    public ResponseEntity<?> createPost(@RequestBody Post post){

        return new ResponseEntity<>(postService.createPost(post), HttpStatus.OK);
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
