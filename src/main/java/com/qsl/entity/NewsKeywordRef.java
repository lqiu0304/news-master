package com.qsl.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 新闻和关键字关联
 */
@Data
public class NewsKeywordRef implements Serializable {
    private static final long serialVersionUID = -5816783232020910492L;
    /**
     * 新闻id
     */
    private Integer newsId;
    /**
     *关键字id
     */
    private Integer keywordId;

    public NewsKeywordRef() {
    }

    public NewsKeywordRef(Integer newsId, Integer keywordId) {
        this.newsId = newsId;
        this.keywordId = keywordId;
    }
}
