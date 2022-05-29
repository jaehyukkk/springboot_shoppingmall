package com.dream.study01.domain.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@DynamicUpdate
public class Post {

    @Column
    @GeneratedValue
    @Id
    private Long id;

    @Column
    private String writer;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Integer hit;

    @Column
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Builder
    public Post(Long id, String writer, String title, String content, Integer hit) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.hit = hit;

    }
}
