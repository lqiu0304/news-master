package com.qsl.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qsl.entity.*;
import com.qsl.enums.NewsCommentStatus;
import com.qsl.mapper.*;
import com.qsl.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsCategoryRefMapper newsCategoryRefMapper;

    @Autowired
    private NewsKeywordRefMapper newsKeywordRefMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Integer countNews(Integer status) {
        Integer count = 0;
        try {
            count = newsMapper.countNews(status);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据状态统计新闻数, status:{}, cause:{}", status, e);
        }
        return count;
    }

    @Override
    public Integer countNewsComment() {
        Integer count = 0;
        try {
            count = newsMapper.countNewsComment();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计新闻评论数失败, cause:{}", e);
        }
        return count;
    }


    @Override
    public Integer countNewsView() {
        Integer count = 0;
        try {
            count = newsMapper.countNewsView();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计新闻访问量失败, cause:{}", e);
        }
        return count;
    }

    @Override
    public Integer countNewsByCategoryId(Integer categoryId) {
        Integer count = 0;
        try {
            count = newsCategoryRefMapper.countNewsByCategoryId(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据分类统计新闻数量失败, categoryId:{}, cause:{}", categoryId, e);
        }
        return count;
    }

    @Override
    public Integer countNewsByKeywordId(Integer keywordId) {
        return newsKeywordRefMapper.countNewsByKeywordId(keywordId);

    }

    @Override
    public List<News> listNews(HashMap<String, Object> criteria) {
        return newsMapper.findAll(criteria);
    }

    @Override
    public List<News> listRecentNews(Integer userId, Integer limit) {
        return newsMapper.listNewsByLimit(userId, limit);
    }

    /**修改新闻*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNewsDetail(News news) {
        news.setNewsUpdateTime(new Date());
        newsMapper.update(news);

        if (news.getKeywordList() != null) {
            //删除关键字和新闻关联
            newsKeywordRefMapper.deleteByNewsId(news.getNewsId());
            //添加关键字和新闻关联
            for (int i = 0; i < news.getKeywordList().size(); i++) {
                NewsKeywordRef newsKeywordRef = new NewsKeywordRef(news.getNewsId(), news.getKeywordList().get(i).getKeywordId());
                newsKeywordRefMapper.insert(newsKeywordRef);
            }
        }

        if (news.getCategoryList() != null) {
            //添加分类和新闻关联
            newsCategoryRefMapper.deleteByNewsId(news.getNewsId());
            //删除分类和新闻关联
            for (int i = 0; i < news.getCategoryList().size(); i++) {
                NewsCategoryRef newsCategoryRef = new NewsCategoryRef(news.getNewsId(), news.getCategoryList().get(i).getCategoryId());
                newsCategoryRefMapper.insert(newsCategoryRef);
            }
        }
    }

    @Override
    public void updateNews(News news) {
        newsMapper.update(news);
    }

    @Override
    public void deleteNewsBatch(List<Integer> ids) {
        newsMapper.deleteBatch(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNews(Integer id) {
        newsMapper.deleteById(id);
        // 删除分类关联
        newsCategoryRefMapper.deleteByNewsId(id);
        // 删除关键字管理
        newsKeywordRefMapper.deleteByNewsId(id);
        // 删除评论
        commentMapper.deleteByNewsId(id);
    }


    @Override
    public PageInfo<News> pageNews(Integer pageIndex,
                                   Integer pageSize,
                                   HashMap<String, Object> criteria) {
        PageHelper.startPage(pageIndex, pageSize);

        /*根据条件查询新闻*/
        List<News> newsList = newsMapper.findAll(criteria);
        for (int i = 0; i < newsList.size(); i++) {
            //封装CategoryList
            List<Category> categoryList = newsCategoryRefMapper.listCategoryByNewsId(newsList.get(i).getNewsId());
            if (categoryList == null || categoryList.size() == 0) {
                categoryList = new ArrayList<>();
                categoryList.add(Category.Default());
            }
            newsList.get(i).setCategoryList(categoryList);

            newsList.get(i).setUser(userMapper.getUserById(newsList.get(i).getNewsUserId()));
//            //封装KeywordList
//            List<Keyword> keywordList = newsKeywordRefMapper.listKeywordByNewsId(newsList.get(i).getNewsId());
//            newsList.get(i).setKeywordList(keywordList);
        }
        return new PageInfo<>(newsList);
    }

    @Override
    public News getNewsByStatusAndId(Integer status, Integer id) {
        News news = newsMapper.getNewsByStatusAndId(status, id);
        if (news != null) {
            List<Category> categoryList = newsCategoryRefMapper.listCategoryByNewsId(news.getNewsId());
            List<Keyword> keywordList = newsKeywordRefMapper.listKeywordByNewsId(news.getNewsId());
            news.setCategoryList(categoryList);
            news.setKeywordList(keywordList);
        }
        return news;
    }


    @Override
    public List<News> listNewsByViewCount(Integer limit) {
        return newsMapper.listNewsByViewCount(limit);
    }

    @Override
    public News getAfterNews(Integer id) {
        return newsMapper.getAfterNews(id);
    }

    @Override
    public News getPreNews(Integer id) {
        return newsMapper.getPreNews(id);
    }

    @Override
    public List<News> listRandomNews(Integer limit) {
        return newsMapper.listRandomNews(limit);
    }

    @Override
    public List<News> listNewsByCommentCount(Integer limit) {
        return newsMapper.listNewsByCommentCount(limit);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertNews(News news) {
        //添加新闻
        news.setNewsCreateTime(new Date());
        news.setNewsUpdateTime(new Date());
        news.setNewsIsComment(NewsCommentStatus.ALLOW.getValue());
        news.setNewsViewCount(0);
        news.setNewsLikeCount(0);
        news.setNewsCommentCount(0);
        news.setNewsOrder(1);
        if (StringUtils.isEmpty(news.getNewsThumbnail())) {
            news.setNewsThumbnail("/img/thumbnail/random/img_" + RandomUtil.randomNumbers(1) + ".jpg");
        }

        newsMapper.insert(news);
        //添加分类和新闻关联
        for (int i = 0; i < news.getCategoryList().size(); i++) {
            NewsCategoryRef newsCategoryRef = new NewsCategoryRef(news.getNewsId(), news.getCategoryList().get(i).getCategoryId());
            newsCategoryRefMapper.insert(newsCategoryRef);
        }
        //添加关键字和新闻关联
        for (int i = 0; i < news.getKeywordList().size(); i++) {
            Integer keywordId = news.getKeywordList().get(i).getKeywordId();
            NewsKeywordRef newsKeywordRef = new NewsKeywordRef(news.getNewsId(), news.getKeywordList().get(i).getKeywordId());
            newsKeywordRefMapper.insert(newsKeywordRef);
        }
    }


    @Override
    public void updateCommentCount(Integer newsId) {
        newsMapper.updateCommentCount(newsId);
    }

    @Override
    public News getLastUpdateNews() {
        return newsMapper.getLastUpdateNews();
    }

    @Override
    public List<News> listNewsByCategoryId(Integer cateId, Integer limit) {
        return newsMapper.findNewsByCategoryId(cateId, limit);
    }

    @Override
    public List<News> listNewsByCategoryIds(List<Integer> cateIds, Integer limit) {
        if (cateIds == null || cateIds.size() == 0) {
            return null;
        }
        return newsMapper.findNewsByCategoryIds(cateIds, limit);
    }

    @Override
    public List<Integer> listCategoryIdByNewsId(Integer newsId) {
        return newsCategoryRefMapper.selectCategoryIdByNewsId(newsId);
    }

    @Override
    public List<News> listAllNotWithContent() {
        return newsMapper.listAllNotWithContent();
    }


}
