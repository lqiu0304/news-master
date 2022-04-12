package com.qsl.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Category implements Serializable {
    private static final long serialVersionUID = 6687286913317513141L;
    /**
     * 分类的id
     */
    private Integer categoryId;
    /**
     * 分类的父id
     */
    private Integer categoryPid;
    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类描述
     */
    private String categoryDescription;
    /**
     * 分类排序值
     */
    private Integer categoryOrder;
    /**
     * 分类图标
     */
    private String categoryIcon;

    /**
     * 新闻数量(非数据库字段)
     */
    private Integer newsCount;

    public Category(Integer categoryId, Integer categoryPid, String categoryName, String categoryDescription, Integer categoryOrder, String categoryIcon,Integer newsCount) {
        this.categoryId = categoryId;
        this.categoryPid = categoryPid;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryOrder = categoryOrder;
        this.categoryIcon = categoryIcon;
        this.newsCount = newsCount;
    }

    public Category(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Category(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Category() {}

    /**
     * 未分类
     * @return 分类
     */
    public static Category Default() {
        return new Category(100000000, "未分类");
    }
}
