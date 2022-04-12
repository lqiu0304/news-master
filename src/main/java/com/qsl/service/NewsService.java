package com.qsl.service;

import com.github.pagehelper.PageInfo;
import com.qsl.entity.News;

import java.util.HashMap;
import java.util.List;

public interface NewsService {
    /**
     * 获取新闻总数
     *
     * @param status 状态
     * @return 数量
     */
    Integer countNews(Integer status);

    /**
     * 获取评论总数
     *
     * @return 数量
     */
    Integer countNewsComment();

    /**
     * 获得浏览量总数
     *
     * @return 数量
     */
    Integer countNewsView();

    /**
     * 统计有这个分类的新闻数
     *
     * @param categoryId 分类ID
     * @return 数量
     */
    Integer countNewsByCategoryId(Integer categoryId);

    /**
     * 统计有这个关键字的新闻数
     *
     * @param keywordId 关键字ID
     * @return 数量
     */
    Integer countNewsByKeywordId(Integer keywordId);


    /**
     * 获得所有新闻不分页
     *
     * @param criteria 查询条件
     * @return 列表
     */
    List<News> listNews(HashMap<String, Object> criteria);

    /**
     * 获得最新新闻
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<News> listRecentNews(Integer userId, Integer limit);


    /**
     * 修改新闻详细信息
     *
     * @param news 新闻
     */
    void updateNewsDetail(News news);

    /**
     * 修改新闻简单信息
     *
     * @param news 新闻
     */
    void updateNews(News news);

    /**
     * 批量删除新闻
     *
     * @param ids 新闻ID
     */
    void deleteNewsBatch(List<Integer> ids);

    /**
     * 删除新闻
     *
     * @param id 新闻ID
     */
    void deleteNews(Integer id);

    /**
     * 分页显示
     *
     * @param pageIndex 第几页开始
     * @param pageSize  一页显示多少
     * @param criteria  查询条件
     * @return 新闻列表
     */
    PageInfo<News> pageNews(Integer pageIndex,
                            Integer pageSize,
                            HashMap<String, Object> criteria);

    /**
     * 新闻详情页面显示
     *
     * @param status 状态
     * @param id     新闻ID
     * @return 新闻
     */
    News getNewsByStatusAndId(Integer status, Integer id);

    /**
     * 获取访问量较多的新闻
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<News> listNewsByViewCount(Integer limit);

    /**
     * 获得上一篇新闻
     *
     * @param id 新闻ID
     * @return 新闻
     */
    News getAfterNews(Integer id);

    /**
     * 获得下一篇新闻
     *
     * @param id 新闻ID
     * @return 新闻
     */
    News getPreNews(Integer id);

    /**
     * 获得随机新闻
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<News> listRandomNews(Integer limit);

    /**
     * 获得评论数较多的新闻
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<News> listNewsByCommentCount(Integer limit);

    /**
     * 添加新闻
     *
     * @param news 新闻
     */
    void insertNews(News news);


    /**
     * 更新新闻的评论数
     *
     * @param newsId 新闻ID
     */
    void updateCommentCount(Integer newsId);

    /**
     * 获得最后更新记录
     *
     * @return 新闻
     */
    News getLastUpdateNews();

    /**
     * 获得相关新闻
     *
     * @param cateId 分类ID
     * @param limit  查询数量
     * @return 列表
     */
    List<News> listNewsByCategoryId(Integer cateId, Integer limit);

    /**
     * 获得相关新闻
     *
     * @param cateIds 分类ID集合
     * @param limit   数量
     * @return 列表
     */
    List<News> listNewsByCategoryIds(List<Integer> cateIds, Integer limit);


    /**
     * 根据新闻ID获得分类ID列表
     *
     * @param newsId 新闻Id
     * @return 列表
     */
    List<Integer> listCategoryIdByNewsId(Integer newsId);

    /**
     * 获得所有的新闻
     *
     * @return 列表
     */
    List<News> listAllNotWithContent();
}
