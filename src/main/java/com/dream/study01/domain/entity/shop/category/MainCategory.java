package com.dream.study01.domain.entity.shop.category;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MainCategory {

    @Column
    @GeneratedValue
    @Id
    private Long id;
    private String subject;
    @Column
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Builder
    public MainCategory(Long id, String subject) {
        this.id = id;
        this.subject = subject;
    }
}
