package com.dream.study01.service;

import com.dream.study01.domain.entity.Post;
import com.dream.study01.domain.repository.PostRepository;
import com.dream.study01.dto.PostDto;
import com.dream.study01.error.ErrorCode;
import com.dream.study01.error.ForbiddenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService){

        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post createPost(PostDto postDto){
        return postRepository.save(postDto.toEntity());
    }

    public List<PostDto> getPostList(){
        List<Post> postList = postRepository.findAllByOrderByIdDesc();
        List<PostDto> postDtoList = new ArrayList<>();

        for(Post post : postList){
            PostDto postDto = PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getWriter())
                    .createdDate(post.getCreatedDate())
                    .build();
            postDtoList.add(postDto);
        }

        return postDtoList;
    }

    public PostDto getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 게시글입니다."));
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .writer(post.getWriter())
                .content(post.getContent())
                .hit(post.getHit())
                .createdDate(post.getCreatedDate())
                .build();

        return postDto;
    }

    @Transactional
    public Integer setPost(PostDto postDto){
        return postRepository.updateTitleContent(postDto.getTitle(), postDto.getContent(), postDto.getId());
    }

    @Transactional
    public void deletePost(Long id){
        Long userId = userService.getMyInfo().getId();
        String postEmail = postRepository.findWriterInPost(id);
        Long postUserId = userService.getUserInfo(postEmail).getId();

        if(userId != postUserId){
            throw new ForbiddenException("권한없음", ErrorCode.FORBIDDEN);
        }

         postRepository.deleteById(id);
    }




}
