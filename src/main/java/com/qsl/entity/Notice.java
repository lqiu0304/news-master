package com.qsl.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告
 */
@Data
public class Notice implements Serializable {

    private static final long serialVersionUID = -4901560494243593100L;
    /**
     * 公告ID
     */
    private Integer noticeId;
    /**
     * 公告标题
     */
    private String noticeTitle;
    /**
     * 内容
     */
    private String noticeContent;
    /**
     * 创建时间
     */
    private Date noticeCreateTime;
    /**
     * 更新时间
     */
    private Date noticeUpdateTime;
    /**
     * 状态
     */
    private Integer noticeStatus;
    /**
     * 排序值
     */
    private Integer noticeOrder;
}