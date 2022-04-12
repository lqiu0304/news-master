package com.qsl.controller.home;

import com.github.pagehelper.PageInfo;

import com.qsl.entity.Keyword;
import com.qsl.entity.News;
import com.qsl.enums.NewsStatus;
import com.qsl.service.KeywordService;
import com.qsl.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;


@Controller
public class KeywordController {

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private NewsService newsService;

    /**
     * 根据关键字查询新闻
     *
     * @param keywordId 关键字ID
     * @return 模板
     */
    @RequestMapping("/keyword/{keywordId}")
    public String getNewsListByKeyword(@PathVariable("keywordId") Integer keywordId,
                                      @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                      Model model) {
        //该关键字信息
        Keyword keyword = keywordService.getKeywordById(keywordId);
        if (keyword == null) {
            return "redirect:/404";
        }
        model.addAttribute("keyword", keyword);

        //新闻列表
        HashMap<String, Object> criteria = new HashMap<>(2);
        criteria.put("keywordId", keywordId);
        criteria.put("status", NewsStatus.PUBLISH.getValue());
        PageInfo<News> newsPageInfo = newsService.pageNews(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", newsPageInfo);

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
        model.addAttribute("pageUrlPrefix", "/keyword/"+keywordId+"?pageIndex");

        return "Home/Page/newsListByKeyword";
    }

}
