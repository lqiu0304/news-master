package com.qsl.mapper;



import com.qsl.entity.Category;
import com.qsl.entity.NewsCategoryRef;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 新闻分类关联表Mapper
 * @author liuyanzhao
 */
@Mapper
public interface NewsCategoryRefMapper {
    
    /**
     * 添加新闻和分类关联记录
     * @param record 关联对象
     * @return 影响行数
     */
    int insert(NewsCategoryRef record);

    /**
     * 根据分类ID删除记录
     * @param categoryId 分类ID
     * @return 影响行数
     */
    int deleteByCategoryId(Integer categoryId);

    /**
     * 根据新闻ID删除记录
     * @param newsId 新闻ID
     * @return 影响行数
     */
    int deleteByNewsId(Integer newsId);

    /**
     * 根据分类ID统计新闻数
     * @param categoryId 分类ID
     * @return 新闻数量
     */
    int countNewsByCategoryId(Integer categoryId);


    /**
     * 根据新闻ID查询分类ID
     *
     * @param newsId 新闻ID
     * @return 分类ID列表
     */
    List<Integer> selectCategoryIdByNewsId(Integer newsId);

    /**
     * 根据分类ID查询新闻ID
     *
     * @param categoryId 分类ID
     * @return 新闻ID列表
     */
    List<Integer> selectNewsIdByCategoryId(Integer categoryId);

    /**
     * 根据新闻ID获得分类列表
     *
     * @param newsId 新闻ID
     * @return 分类列表
     */
    List<Category> listCategoryByNewsId(Integer newsId);

}