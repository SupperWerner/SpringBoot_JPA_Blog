package com.blog.blog.dao;

import com.blog.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);

    /**
     * 查询前六条 博客最多的 类别
     * @param pageble
     * @return
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageble);
}
