package com.qsl.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = -4415517704211731385L;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 登录密码
     */
    private String userPass;
    /**
     * 用户昵称
     */
    private String userNickname;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 个人主页
     */
    private String userUrl;
    /**
     * 头像
     */
    private String userAvatar;
    /**
     * 登录ip
     */
    private String userLastLoginIp;
    /**
     * 注册时间
     */
    private Date userRegisterTime;
    /**
     * 登录时间
     */
    private Date userLastLoginTime;
    /**
     * 状态
     */
    private Integer userStatus;
    /**
     * 用户角色：1、2、3
     */
    private String userRole;

    /**
     * 新闻数量（不是数据库字段）
     */
    private Integer newsCount;
}
