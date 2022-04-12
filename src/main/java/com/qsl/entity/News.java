package com.qsl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class News implements Serializable {

    private static final long serialVersionUID = 5207865247400761539L;
    /**
     * 新闻id
     */
    private Integer newsId;
    /**
     * 用户id
     */
    private Integer newsUserId;
    /**
     * 新闻标题
     */
    private String newsTitle;
    /**
     * 访问量
     */
    private Integer newsViewCount;
    /**
     * 新闻评论数
     */
    private Integer newsCommentCount;
    /**
     * 新闻点赞数量
     */
    private Integer newsLikeCount;
    /**
     * 新闻发布时间
     */
    private Date newsCreateTime;
    /**
     * 新闻修改时间
     */
    private Date newsUpdateTime;
    /**
     * 是否允许评论
     */
    private Integer newsIsComment;
    /**
     * 新闻状态
     */
    private Integer newsStatus;
    /**
     * 新闻排序值
     */
    private Integer newsOrder;
    /**
     * 新闻内容
     */
    private String newsContent;
    /**
     * 新闻摘要
     */
    private String newsSummary;
    /**
     * 新闻封面
     */
    private String newsThumbnail;
    /**
     * 用户
     */
    private User user;
    /**
     * 新闻关键字
     */
    private List<Keyword> keywordList;
    /**
     * 新闻类型
     */
    private List<Category> categoryList;

}