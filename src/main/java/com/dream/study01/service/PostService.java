package com.dream.study01.service;

import com.dream.study01.domain.entity.Post;
import com.dream.study01.domain.repository.PostRepository;
import com.dream.study01.dto.PostDto;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public Post createPost(PostDto postDto){

        return postRepository.save(postDto.toEntity());
    }


}
