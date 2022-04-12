package com.qsl.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 关键字
 */
@Data
public class Keyword implements Serializable {
    private static final long serialVersionUID = 605449151900057035L;
    /**
     * 关键字id
     */
    private Integer keywordId;
    /**
     * 关键自名称
     */
    private String keywordName;
    /**
     * 关键字描述
     */
    private String keywordDescription;

    /**
     * 新闻数量(不是数据库字段)
     */
    private Integer newsCount;

    public Keyword() {
    }

    public Keyword(Integer keywordId) {
        this.keywordId = keywordId;
    }
    public Keyword(String keywordName) {
        this.keywordName = keywordName;
    }

    public Keyword(Integer keywordId, String keywordName, String keywordDescription, Integer newsCount) {
        this.keywordId = keywordId;
        this.keywordName = keywordName;
        this.keywordDescription = keywordDescription;
        this.newsCount = newsCount;
    }
}