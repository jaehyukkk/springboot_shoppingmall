package com.dream.study01.dto;

import com.dream.study01.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private Integer hit;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    public Post toEntity(){
        Post post = Post.builder()
                .id(id)
                .writer(writer)
                .content(content)
                .title(title)
                .hit(hit)
                .build();
        return post;
    }

//    public PostDto(Long id, String title, String content){
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }
//
//    public Post toEntityUpdate(){
//        Post post = Post.builder()
//                .id(id)
//                .content(content)
//                .title(title)
//                .build();
//        return post;
//    }

    @Builder
    public PostDto(Long id, String writer, String title, String content, Integer hit, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }
}
