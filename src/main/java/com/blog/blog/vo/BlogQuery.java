package com.blog.blog.vo;

import lombok.Data;

/**
 * 博客页面资源查询页面
 */
@Data
public class BlogQuery {
    private String title;
    private Long typeId;
    private boolean recommend;
}
