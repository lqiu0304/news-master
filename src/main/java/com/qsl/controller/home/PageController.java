package com.qsl.controller.home;



import com.qsl.entity.Category;
import com.qsl.entity.Keyword;
import com.qsl.entity.News;
import com.qsl.entity.Page;
import com.qsl.service.CategoryService;
import com.qsl.service.KeywordService;
import com.qsl.service.NewsService;
import com.qsl.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class PageController {

    @Autowired
    private PageService pageService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private KeywordService keywordService;

    /**
     * 页面详情页面
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/{key}")
    public String pageDetail(@PathVariable("key") String key, Model model) {
        Page page = pageService.getPageByKey(1, key);
        if (page == null) {
            return "redirect:/404";
        }
        model.addAttribute("page", page);

        //侧边栏显示
        //获得热评新闻
        List<News> mostCommentNewsList = newsService.listNewsByCommentCount(8);
        model.addAttribute("mostCommentNewsList", mostCommentNewsList);
        return "Home/Page/page";

    }


    /**
     * 新闻归档页面显示
     *
     * @return
     */
    @RequestMapping(value = "/newsFile")
    public String newsFile(Model model) {
        List<News> newsList = newsService.listAllNotWithContent();
        model.addAttribute("newsList", newsList);
        //侧边栏显示
        //获得热评新闻
        List<News> mostCommentNewsList = newsService.listNewsByCommentCount(10);
        model.addAttribute("mostCommentNewsList", mostCommentNewsList);
        return "Home/Page/newsFile";
    }

    /**
     * 站点地图显示
     *
     * @return
     */
    @RequestMapping(value = "/map")
    public String siteMap(Model model) {
        //新闻显示
        List<News> newsList = newsService.listAllNotWithContent();
        model.addAttribute("newsList", newsList);
        //分类显示
        List<Category> categoryList = categoryService.listCategory();
        model.addAttribute("categoryList", categoryList);
        //关键字显示
        List<Keyword> keywordList = keywordService.listKeyword();
        model.addAttribute("keywordList", keywordList);

        //侧边栏显示
        //获得热评新闻
        List<News> mostCommentNewsList = newsService.listNewsByCommentCount(10);
        model.addAttribute("mostCommentNewsList", mostCommentNewsList);
        return "Home/Page/siteMap";
    }

    /**
     * 留言板
     *
     * @return
     */
    @RequestMapping(value = "/message")
    public String message(Model model) {

        //侧边栏显示
        //获得热评新闻
        List<News> mostCommentNewsList = newsService.listNewsByCommentCount(8);
        model.addAttribute("mostCommentNewsList", mostCommentNewsList);
        return "Home/Page/message";
    }
}
