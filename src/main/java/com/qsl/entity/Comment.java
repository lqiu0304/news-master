package com.qsl.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新闻评论
 */
@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = -1038897351672911219L;

    /**
     * 评论id
     */
    private Integer commentId;
    /**
     * 上级评论id
     */
    private Integer commentPid;
    /**
     * 上级评论名称
     */
    private String commentPname;
    /**
     * 新闻id
     */
    private Integer commentNewsId;
    /**
     * 评论名名称
     */
    private String commentAuthorName;
    /**
     * 评论邮箱
     */
    private String commentAuthorEmail;
    /**
     * 评论人个人主页
     */
    private String commentAuthorUrl;
    /**
     * 头像
     */
    private String commentAuthorAvatar;
    /**
     * 评论内容
     */
    private String commentContent;
    /**
     * 评论浏览器信息
     */
    private String commentAgent;
    /**
     * 历览器ip
     */
    private String commentIp;
    /**
     * 评论时间
     */
    private Date commentCreateTime;

    /**
     * 角色(管理员1，访客0)
     */
    private Integer commentRole;

    /**
     * 评论用户ID
     */
    private Integer commentUserId;

    /**
     * 新闻对象
     * 非数据库字段
     */
    private News news;
}