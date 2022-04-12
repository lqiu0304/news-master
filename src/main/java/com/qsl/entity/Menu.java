package com.qsl.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单
 */
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = 489914127235951698L;
    /**
     * 菜单ID
     */
    private Integer menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单URL
     */
    private String menuUrl;
    /**
     * 菜单等级
     */
    private Integer menuLevel;
    /**
     * 菜单图标
     */
    private String menuIcon;
    /**
     * 菜单排序值
     */
    private Integer menuOrder;

}