package com.dream.study01.domain.repository.shop.goods;

import com.dream.study01.domain.entity.shop.category.MainCategory;
import com.dream.study01.domain.entity.shop.category.SubCategory;
import com.dream.study01.domain.entity.shop.goods.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
    @Query(value = "select distinct u from Goods u left join fetch u.files")
    public List<Goods> findAllFetch();

    @Query(value = "select distinct u from Goods u left join fetch u.files where u.id = :id")
    public Goods findByIdFetch(@Param("id") Long id);

    List<Goods> findAllByMainCategory(MainCategory mainCategory);
    Page<Goods> findAllByMainCategoryAndSubCategory(MainCategory mainCategory, SubCategory subCategory, Pageable pageable);
}
