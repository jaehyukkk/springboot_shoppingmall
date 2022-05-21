package com.dream.study01.domain.repository;

import com.dream.study01.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post ,Long> {
}
