package com.qsl.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Data
public class Page implements Serializable{

    private static final long serialVersionUID = 3927496662110298634L;
    /**
     * 自定义页面ID
     */
    private Integer pageId;
    /**
     * key
     */
    private String pageKey;
    /**
     * 标题
     */
    private String pageTitle;
    /**
     * 内容
     */
    private String pageContent;
    /**
     * 创建时间
     */
    private Date pageCreateTime;
    /**
     * 更新时间
     */
    private Date pageUpdateTime;
    /**
     * 访问量
     */
    private Integer pageViewCount;
    /**
     * 评论数
     */
    private Integer pageCommentCount;
    /**
     * 状态
     */
    private Integer pageStatus;

}