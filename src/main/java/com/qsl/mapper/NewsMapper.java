package com.qsl.mapper;

import com.qsl.entity.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface NewsMapper {
    /**
     * 根据ID删除
     *
     * @param newsId 新闻ID
     * @return 影响函数
     */
    Integer deleteById(Integer newsId);

    /**
     * 根据用户ID删除
     *
     * @param userId 用户ID
     * @return 影响函数
     */
    Integer deleteByUserId(Integer userId);

    /**
     * 添加新闻
     *
     * @param news 新闻
     * @return 新闻
     */
    Integer insert(News news);

    /**
     * 更新新闻
     *
     * @param news 新闻
     * @return 影响行数
     */
    Integer update(News news);

    /**
     * 获得所有的新闻
     *
     * @param criteria 查询条件
     * @return 新闻列表
     */
    List<News> findAll(HashMap<String, Object> criteria);

    /**
     * 新闻归档
     *
     * @return
     */
    List<News> listAllNotWithContent();

    /**
     * 获取新闻总数
     *
     * @param status 状态
     * @return 数量
     */
    Integer countNews(@Param(value = "status") Integer status);

    /**
     * 获得留言总数
     *
     * @return 数量
     */
    Integer countNewsComment();

    /**
     * 获得浏览量总数
     *
     * @return 新闻数量
     */
    Integer countNewsView();

    /**
     * 获得所有新闻(新闻归档)
     *
     * @return 新闻列表
     */
    List<News> listNews();

    /**
     * 根据id查询用户信息
     *
     * @param status 状态
     * @param id     新闻ID
     * @return 新闻
     */
    News getNewsByStatusAndId(@Param(value = "status") Integer status, @Param(value = "id") Integer id);

    /**
     * 分页操作
     *
     * @param status    状态
     * @param pageIndex 从第几页开始
     * @param pageSize  数量
     * @return 新闻列表
     */
    @Deprecated
    List<News> pageNews(@Param(value = "status") Integer status,
                              @Param(value = "pageIndex") Integer pageIndex,
                              @Param(value = "pageSize") Integer pageSize);


    /**
     * 获得访问最多的新闻(猜你喜欢)
     *
     * @param limit 查询数量
     * @return 新闻列表
     */
    List<News> listNewsByViewCount(@Param(value = "limit") Integer limit);

    /**
     * 获得上一篇新闻
     *
     * @param id 新闻ID
     * @return 新闻
     */
    News getAfterNews(@Param(value = "id") Integer id);

    /**
     * 获得下一篇新闻
     *
     * @param id 新闻ID
     * @return 新闻
     */
    News getPreNews(@Param(value = "id") Integer id);

    /**
     * 获得随机新闻
     *
     * @param limit 查询数量
     * @return 新闻列表
     */
    List<News> listRandomNews(@Param(value = "limit") Integer limit);

    /**
     * 热评新闻
     *
     * @param limit 查询数量
     * @return 新闻列表
     */
    List<News> listNewsByCommentCount(@Param(value = "limit") Integer limit);


    /**
     * 更新新闻的评论数
     *
     * @param newsId 新闻ID
     */
    void updateCommentCount(@Param(value = "newsId") Integer newsId);

    /**
     * 获得最后更新的记录
     *
     * @return 新闻
     */
    News getLastUpdateNews();

    /**
     * 用户的新闻数
     *
     * @param id 用户ID
     * @return 数量
     */
    Integer countNewsByUser(@Param(value = "id") Integer id);

    /**
     * 根据分类ID
     *
     * @param categoryId 分类ID
     * @param limit      查询数量
     * @return 新闻列表
     */
    List<News> findNewsByCategoryId(@Param("categoryId") Integer categoryId,
                                          @Param("limit") Integer limit);

    /**
     * 根据分类ID
     *
     * @param categoryIds 分类ID集合
     * @param limit       查询数量
     * @return 新闻列表
     */
    List<News> findNewsByCategoryIds(@Param("categoryIds") List<Integer> categoryIds,
                                           @Param("limit") Integer limit);

    /**
     * 获得最新新闻
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<News> listNewsByLimit(@Param("userId") Integer userId, @Param("limit") Integer limit);

    /**
     * 批量删除新闻
     *
     * @param ids 新闻Id列表
     * @return 影响行数
     */
    Integer deleteBatch(@Param("ids") List<Integer> ids);

    /**
     * 获得一个用户的新闻id集合
     *
     * @param userId
     * @return
     */
    List<Integer> listNewsIdsByUserId(Integer userId);
}
