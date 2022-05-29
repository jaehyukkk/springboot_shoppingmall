package com.dream.study01.domain.entity.shop.category;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class SubCategory {

        @Column
        @GeneratedValue
        @Id
        private Long id;
        private String subject;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "MainCategory_id")
        private MainCategory mainCategory;

        @Column
        @CreatedDate
        private LocalDateTime createdDate;
        @LastModifiedDate
        private LocalDateTime updatedDate;

        @Builder
        public SubCategory(Long id, String subject, MainCategory mainCategory) {
                this.id = id;
                this.subject = subject;
                this.mainCategory = mainCategory;
        }
}