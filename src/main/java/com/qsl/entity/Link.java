package com.qsl.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 友情链接
 */
@Data
public class Link implements Serializable {
    private static final long serialVersionUID = -259829372268790508L;
    /**
     * 链接ID
     */
    private Integer linkId;
    /**
     *URL
     */
    private String linkUrl;
    /**
     *姓名
     */
    private String linkName;
    /**
     *头像
     */
    private String linkImage;
    /**
     *描述
     */
    private String linkDescription;
    /**
     *所属人名称
     */
    private String linkOwnerNickname;
    /**
     *联系方式
     */
    private String linkOwnerContact;
    /**
     * 更新时间
     */
    private Date linkUpdateTime;
    /**
     *创建时间
     */
    private Date linkCreateTime;
    /**
     *排序号
     */
    private Integer linkOrder;
    /**
     *状态
     */
    private Integer linkStatus;

}