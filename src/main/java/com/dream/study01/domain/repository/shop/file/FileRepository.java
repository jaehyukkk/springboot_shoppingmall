package com.dream.study01.domain.repository.shop.file;

import com.dream.study01.domain.entity.shop.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
