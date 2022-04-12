package com.qsl.controller.home;


import com.alibaba.fastjson.JSON;

import com.qsl.entity.Comment;
import com.qsl.entity.Keyword;
import com.qsl.entity.News;
import com.qsl.entity.User;
import com.qsl.enums.NewsStatus;
import com.qsl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 新闻的controller

 */
@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新闻详情页显示
     *
     * @param newsId 新闻ID
     * @return modelAndView
     */
    @RequestMapping(value = "/news/{newsId}")
    public String getNewsDetailPage(@PathVariable("newsId") Integer newsId, Model model) {

        //新闻信息，分类，关键字，作者，评论
        News news = newsService.getNewsByStatusAndId(NewsStatus.PUBLISH.getValue(), newsId);
        if (news == null) {
            return "Home/Error/404";
        }

        //用户信息
        User user = userService.getUserById(news.getNewsUserId());
        news.setUser(user);

        //新闻信息
        model.addAttribute("news", news);

        //评论列表
        List<Comment> commentList = commentService.listCommentByNewsId(newsId);
        model.addAttribute("commentList", commentList);

        //相关新闻
        List<Integer> categoryIds = newsService.listCategoryIdByNewsId(newsId);
        List<News> similarNewsList = newsService.listNewsByCategoryIds(categoryIds, 5);
        model.addAttribute("similarNewsList", similarNewsList);

        //猜你喜欢
        List<News> mostViewNewsList = newsService.listNewsByViewCount(5);
        model.addAttribute("mostViewNewsList", mostViewNewsList);

        //获取下一篇新闻
        News afterNews = newsService.getAfterNews(newsId);
        model.addAttribute("afterNews", afterNews);

        //获取上一篇新闻
        News preNews = newsService.getPreNews(newsId);
        model.addAttribute("preNews", preNews);

        //侧边栏
        //关键字列表显示
        List<Keyword> allKeywordList = keywordService.listKeyword();
        model.addAttribute("allKeywordList", allKeywordList);
        //获得随机新闻
        List<News> randomNewsList = newsService.listRandomNews(8);
        model.addAttribute("randomNewsList", randomNewsList);
        //获得热评新闻
        List<News> mostCommentNewsList = newsService.listNewsByCommentCount(8);
        model.addAttribute("mostCommentNewsList", mostCommentNewsList);

        return "Home/Page/newsDetail";
    }

    /**
     * 点赞增加
     *
     * @param id 新闻ID
     * @return 点赞量数量
     */
    @RequestMapping(value = "/news/like/{id}", method = {RequestMethod.POST}, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String increaseLikeCount(@PathVariable("id") Integer id) {
        News news = newsService.getNewsByStatusAndId(NewsStatus.PUBLISH.getValue(), id);
        Integer newsCount = news.getNewsLikeCount() + 1;
        news.setNewsLikeCount(newsCount);
        newsService.updateNews(news);
        return JSON.toJSONString(newsCount);
    }

    /**
     * 新闻访问量数增加
     *
     * @param id 新闻ID
     * @return 访问量数量
     */
    @RequestMapping(value = "/news/view/{id}", method = {RequestMethod.POST}, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String increaseViewCount(@PathVariable("id") Integer id) {
        News news = newsService.getNewsByStatusAndId(NewsStatus.PUBLISH.getValue(), id);
        Integer newsCount = news.getNewsViewCount() + 1;
        news.setNewsViewCount(newsCount);
        newsService.updateNews(news);
        return JSON.toJSONString(newsCount);
    }


}
