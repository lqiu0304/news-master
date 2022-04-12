package com.qsl.mapper;


import com.qsl.entity.Keyword;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KeywordMapper {

    /**
     * 根据ID删除
     * 
     * @param keywordId 关键字ID
     * @return 影响行数
     */
    int deleteById(Integer keywordId);

    /**
     * 添加
     * 
     * @param keyword 关键字
     * @return 影响行数
     */
    int insert(Keyword keyword);

    /**
     * 根据ID查询
     *
     * @param keywordId 关键字ID
     * @return 关键字
     */
    Keyword getKeywordById(Integer keywordId);

    /**
     * 更新
     * @param keyword 关键字
     * @return 影响行数
     */
    int update(Keyword keyword);

    /**
     * 获得关键字总数
     * 
     * @return 数量
     */
    Integer countKeyword() ;

    /**
     * 获得关键字列表
     * 
     * @return 列表
     */
    List<Keyword> listKeyword() ;


    /**
     * 根据关键字名获取关键字
     * 
     * @param name 名称
     * @return 关键字
     */
    Keyword  getKeywordByName(String name) ;
}