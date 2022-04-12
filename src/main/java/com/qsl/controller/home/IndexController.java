package com.qsl.controller.home;

import com.github.pagehelper.PageInfo;

import com.qsl.entity.*;
import com.qsl.enums.LinkStatus;
import com.qsl.enums.NewsStatus;
import com.qsl.enums.NoticeStatus;
import com.qsl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * 用户的controller

 */
@Controller
public class IndexController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = {"/", "/news"})
    public String index(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
        HashMap<String, Object> criteria = new HashMap<>(1);
        criteria.put("status", NewsStatus.PUBLISH.getValue());
        //新闻列表
        PageInfo<News> newsList = newsService.pageNews(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", newsList);

        //公告
        List<Notice> noticeList = noticeService.listNotice(NoticeStatus.NORMAL.getValue());
        model.addAttribute("noticeList", noticeList);
        //友情链接
        List<Link> linkList = linkService.listLink(LinkStatus.NORMAL.getValue());
        model.addAttribute("linkList", linkList);

        //侧边栏显示
        //关键字列表显示
        List<Keyword> allKeywordList = keywordService.listKeyword();
        model.addAttribute("allKeywordList", allKeywordList);
        //最新评论
        List<Comment> recentCommentList = commentService.listRecentComment(null, 10);
        model.addAttribute("recentCommentList", recentCommentList);
        model.addAttribute("pageUrlPrefix", "/news?pageIndex");
        return "Home/index";
    }

    @RequestMapping(value = "/search")
    public String search(
            @RequestParam("keywords") String keywords,
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize, Model model) {
        //新闻列表
        HashMap<String, Object> criteria = new HashMap<>(2);
        criteria.put("status", NewsStatus.PUBLISH.getValue());
        criteria.put("keywords", keywords);
        PageInfo<News> newsPageInfo = newsService.pageNews(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", newsPageInfo);

        //侧边栏显示
        //关键字列表显示
        List<Keyword> allKeywordList = keywordService.listKeyword();
        model.addAttribute("allKeywordList", allKeywordList);
        //获得随机新闻
        List<News> randomNewsList = newsService.listRandomNews(8);
        model.addAttribute("randomNewsList", randomNewsList);
        //获得热评新闻
        List<News> mostCommentNewsList = newsService.listNewsByCommentCount(8);
        model.addAttribute("mostCommentNewsList", mostCommentNewsList);
        //最新评论
        List<Comment> recentCommentList = commentService.listRecentComment(null, 10);
        model.addAttribute("recentCommentList", recentCommentList);
        model.addAttribute("pageUrlPrefix", "/search?pageIndex");
        return "Home/Page/search";
    }

    @RequestMapping("/404")
    public String NotFound(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        return "Home/Error/404";
    }


    @RequestMapping("/403")
    public String Page403(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        return "Home/Error/403";
    }

    @RequestMapping("/500")
    public String ServerError(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        return "Home/Error/500";
    }


}




