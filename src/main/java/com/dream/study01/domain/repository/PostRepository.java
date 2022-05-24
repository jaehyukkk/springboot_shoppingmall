package com.dream.study01.domain.repository;

import com.dream.study01.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post ,Long> {
    @Modifying
    @Query(value = "update Post p set p.title = :title, p.content = :content where p.id = :id", nativeQuery = true)
    Integer updateTitleContent(@Param("title") String title,
                            @Param("content") String content,
                            @Param("id") Long id);

    public List<Post> findAllByOrderByIdDesc();

    @Query(value = "select writer w from Post p where p.id = :id", nativeQuery = true)
    String findWriterInPost(@Param("id") Long id);
}
