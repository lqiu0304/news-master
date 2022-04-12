package com.qsl.mapper;

import com.qsl.entity.Keyword;
import com.qsl.entity.NewsKeywordRef;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 新闻关键字关联表Mapper
 * @author liuyanzhao
 */
@Mapper
public interface NewsKeywordRefMapper {
    
    /**
     * 添加新闻和关键字关联记录
     * @param record 关联对象
     * @return 影响行数
     */
    int insert(NewsKeywordRef record);

    /**
     * 根据关键字ID删除记录
     * @param keywordId 关键字ID
     * @return 影响行数
     */
    int deleteByKeywordId(Integer keywordId);

    /**
     * 根据新闻ID删除记录
     * @param newsId 新闻ID
     * @return 影响行数
     */
    int deleteByNewsId(Integer newsId);

    /**
     * 根据关键字ID统计新闻数
     * @param keywordId 关键字ID
     * @return 新闻数量
     */
    int countNewsByKeywordId(Integer keywordId);

    /**
     * 根据新闻id获得关键字列表
     *
     * @param newsId 新闻ID
     * @return 关键字列表
     */
    List<Keyword> listKeywordByNewsId(Integer newsId);


}