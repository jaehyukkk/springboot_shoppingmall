package com.dream.study01.domain.repository.shop.category;

import com.dream.study01.domain.entity.shop.category.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {

}
