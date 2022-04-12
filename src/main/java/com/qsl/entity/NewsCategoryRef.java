package com.qsl.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 新闻分类关联表
 */
@Data
public class NewsCategoryRef implements Serializable {

    private static final long serialVersionUID = -6809206515467725995L;
    /**
     * 新闻id
     */
    private Integer newsId;
    /**
     * 分类id
     */
    private Integer categoryId;

    public NewsCategoryRef() {
    }

    public NewsCategoryRef(Integer newsId, Integer categoryId) {
        this.newsId = newsId;
        this.categoryId = categoryId;
    }
}