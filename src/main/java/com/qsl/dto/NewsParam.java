package com.qsl.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewsParam {

    private Integer newsId;

    private String newsTitle;

    private String newsContent;

    private Integer newsParentCategoryId;

    private Integer newsChildCategoryId;

    private Integer newsOrder;

    private Integer newsStatus;

    private String newsThumbnail;

    private List<Integer> newsKeywordIds;
    private String newsKeywords;

}
