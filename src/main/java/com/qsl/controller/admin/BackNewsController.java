package com.qsl.controller.admin;

import cn.hutool.http.HtmlUtil;

import com.github.pagehelper.PageInfo;
import com.qsl.dto.NewsParam;
import com.qsl.entity.Category;
import com.qsl.entity.Keyword;
import com.qsl.entity.News;
import com.qsl.entity.User;
import com.qsl.enums.UserRole;
import com.qsl.service.CategoryService;
import com.qsl.service.KeywordService;
import com.qsl.service.NewsService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/admin/news")
public class BackNewsController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 后台新闻列表显示
     *
     * @return modelAndView
     */
    @RequestMapping(value = "")
    public String index(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) String status, Model model,
                        HttpSession session) {
        HashMap<String, Object> criteria = new HashMap<>(1);
        if (status == null) {
            model.addAttribute("pageUrlPrefix", "/admin/news?pageIndex");
        } else {
            criteria.put("status", status);
            model.addAttribute("pageUrlPrefix", "/admin/news?status=" + status + "&pageIndex");
        }

        User user = (User) session.getAttribute("user");
        if (!UserRole.ADMIN.getValue().equals(user.getUserRole())) {
            // 如果是普通用户，设置查询条件为自己id
            criteria.put("userId", user.getUserId());
        }
        /*调用分页查询*/
        PageInfo<News> newsPageInfo = newsService.pageNews(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", newsPageInfo);
        return "Admin/News/index";
    }

    /**
     * 后台新闻审核列表显示
     *
     * @return modelAndView
     */
    @RequestMapping(value = "audit")
    public String check(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) String status, Model model,
                        HttpSession session) {
        HashMap<String, Object> criteria = new HashMap<>(1);
        if (status == null) {
            model.addAttribute("pageUrlPrefix", "/admin/news?pageIndex");
        } else {
            criteria.put("status", status);
            model.addAttribute("pageUrlPrefix", "/admin/news?status=" + status + "&pageIndex");
        }

        User user = (User) session.getAttribute("user");
        if (!UserRole.ADMIN.getValue().equals(user.getUserRole())) {
            // 用户查询自己的新闻, 管理员查询所有的
            criteria.put("userId", user.getUserId());
        }
        PageInfo<News> newsPageInfo = newsService.pageNews(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", newsPageInfo);
        return "Admin/News/audit";
    }



    /**
     * 后台添加新闻页面显示
     *
     * @return
     */
    @RequestMapping(value = "/insert")
    public String insertNewsView(Model model) {
        List<Category> categoryList = categoryService.listCategory();
        List<Keyword> keywordList = keywordService.listKeyword();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("keywordList", keywordList);
        return "Admin/News/insert";
    }

    /**
     * 后台添加新闻提交操作
     *
     * @param newsParam
     * @return
     */
    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public String insertNewsSubmit(HttpSession session, NewsParam newsParam) {
        News news = new News();
        //用户ID
        User user = (User) session.getAttribute("user");
        if (user != null) {
            news.setNewsUserId(user.getUserId());
        }
        news.setNewsTitle(newsParam.getNewsTitle());
        //新闻摘要
        int summaryLength = 150;
        String summaryText = HtmlUtil.cleanHtmlTag(newsParam.getNewsContent());

        if (summaryText.length() > summaryLength) {
            String summary = summaryText.substring(0, summaryLength);
            news.setNewsSummary(summary);
        } else {
            news.setNewsSummary(summaryText);
        }
        news.setNewsThumbnail(newsParam.getNewsThumbnail());
        news.setNewsContent(newsParam.getNewsContent());
        news.setNewsStatus(newsParam.getNewsStatus());
        //填充分类
        List<Category> categoryList = new ArrayList<>();
        if (newsParam.getNewsChildCategoryId() != null) {
            categoryList.add(new Category(newsParam.getNewsParentCategoryId()));
        }
        if (newsParam.getNewsChildCategoryId() != null) {
            categoryList.add(new Category(newsParam.getNewsChildCategoryId()));
        }
        news.setCategoryList(categoryList);


        /*添加关键字*/
        /*准备集合装关键字*/
        List<Keyword> keywordList = new ArrayList<>();
        /*接收到的关键字为字符串*/
        String newsKeyword = newsParam.getNewsKeywords();
        if (newsKeyword != null) {
            /*对关键字的空格切割*/
            String[] newsKeywords = newsKeyword.trim().split("\\s+");
            for (String keywordName : newsKeywords) {
                Keyword keyword = new Keyword(keywordName);
                /*添加关键字到数据库*/
                Keyword insertKeyword = keywordService.insertKeyword(keyword);

                /*添加到集合中*/
                keywordList.add(insertKeyword);
            }
        }
        /*设置到News对象属性中*/
        news.setKeywordList(keywordList);

        newsService.insertNews(news);
        return "redirect:/admin/news";
    }


    /**
     * 删除新闻
     *
     * @param id 新闻ID
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void deleteNews(@PathVariable("id") Integer id, HttpSession session) {
        News dbNews = newsService.getNewsByStatusAndId(null, id);
        if (dbNews == null) {
            return;
        }
        User user = (User) session.getAttribute("user");
        // 如果不是管理员，访问其他用户的数据，则跳转403
        if (!Objects.equals(dbNews.getNewsUserId(), user.getUserId()) && !Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) {
            return;
        }
        newsService.deleteNews(id);
    }


    /**
     * 编辑新闻页面显示
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public String editNewsView(@PathVariable("id") Integer id, Model model, HttpSession session) {

        News news = newsService.getNewsByStatusAndId(null, id);
        if (news == null) {
            return "redirect:/404";
        }
        User user = (User) session.getAttribute("user");
        // 如果不是管理员，访问其他用户的数据，则跳转403
        if (!Objects.equals(news.getNewsUserId(), user.getUserId()) && !Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) {
            return "redirect:/403";
        }

        model.addAttribute("news", news);

        List<Category> categoryList = categoryService.listCategory();
        model.addAttribute("categoryList", categoryList);

        List<Keyword> keywordList = keywordService.listKeywordByNewsId(id);
        StringBuilder keywordName = new StringBuilder();
        for (Keyword keyword : keywordList) {
            keywordName.append(keyword.getKeywordName()).append(" ");
        }

        model.addAttribute("keywordName", keywordName);

        return "Admin/News/edit";
    }


    /**
     * 编辑新闻提交
     *
     * @param newsParam
     * @return
     */
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editNewsSubmit(NewsParam newsParam, HttpSession session) {
        News dbNews = newsService.getNewsByStatusAndId(null, newsParam.getNewsId());
        if (dbNews == null) {
            return "redirect:/404";
        }
        User user = (User) session.getAttribute("user");
        // 如果不是管理员，访问其他用户的数据，则跳转403
        if (!Objects.equals(dbNews.getNewsUserId(), user.getUserId()) && !Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) {
            return "redirect:/403";
        }
        News news = new News();
        news.setNewsThumbnail(newsParam.getNewsThumbnail());
        news.setNewsId(newsParam.getNewsId());
        news.setNewsTitle(newsParam.getNewsTitle());
        news.setNewsContent(newsParam.getNewsContent());
        news.setNewsStatus(newsParam.getNewsStatus());
        //新闻摘要
        int summaryLength = 150;
        String summaryText = HtmlUtil.cleanHtmlTag(news.getNewsContent());
        if (summaryText.length() > summaryLength) {
            String summary = summaryText.substring(0, summaryLength);
            news.setNewsSummary(summary);
        } else {
            news.setNewsSummary(summaryText);
        }
        //填充分类
        List<Category> categoryList = new ArrayList<>();
        if (newsParam.getNewsChildCategoryId() != null) {
            categoryList.add(new Category(newsParam.getNewsParentCategoryId()));
        }
        if (newsParam.getNewsChildCategoryId() != null) {
            categoryList.add(new Category(newsParam.getNewsChildCategoryId()));
        }
        news.setCategoryList(categoryList);

        /*添加关键字*/
        /*准备集合装关键字*/
        List<Keyword> keywordList = new ArrayList<>();
        /*接收到的关键字为字符串*/
        String newsKeyword = newsParam.getNewsKeywords();
        if (newsKeyword != null) {
            /*对关键字的空格切割*/
            String[] newsKeywords = newsKeyword.trim().split("\\s+");
            for (String keywordName : newsKeywords) {
                Keyword keyword = new Keyword(keywordName);
                /*添加关键字到数据库*/
                Keyword insertKeyword = keywordService.insertKeyword(keyword);
                /*添加到集合中*/
                keywordList.add(insertKeyword);
            }
        }
        /*设置到News对象属性中*/
        news.setKeywordList(keywordList);

/*        //填充关键字
        List<Keyword> keywordList = new ArrayList<>();
        if (newsParam.getNewsKeywords() != null) {
            for (int i = 0; i < newsParam.getNewsKeywords().size(); i++) {
                Keyword keyword = new Keyword(newsParam.getNewsKeywords().get(i));
                keywordList.add(keyword);
            }
        }
        news.setKeywordList(keywordList);*/


        newsService.updateNewsDetail(news);
        return "redirect:/admin/news";
    }

    /**
     * 后台添加新闻草稿提交操作
     *
     * @param newsParam
     * @return
     */
    @RequestMapping(value = "/insertDraftSubmit", method = RequestMethod.POST)
    public String insertDraftSubmit(HttpSession session, NewsParam newsParam) {
        News news = new News();
        //用户ID
        User user = (User) session.getAttribute("user");
        if (user != null) {
            news.setNewsUserId(user.getUserId());
        }
        news.setNewsTitle(newsParam.getNewsTitle());
        //新闻摘要
        int summaryLength = 150;
        String summaryText = HtmlUtil.cleanHtmlTag(newsParam.getNewsContent());
        if (summaryText.length() > summaryLength) {
            String summary = summaryText.substring(0, summaryLength);
            news.setNewsSummary(summary);
        } else {
            news.setNewsSummary(summaryText);
        }
        news.setNewsThumbnail(newsParam.getNewsThumbnail());
        news.setNewsContent(newsParam.getNewsContent());
        news.setNewsStatus(newsParam.getNewsStatus());
        //填充分类
        List<Category> categoryList = new ArrayList<>();
        if (newsParam.getNewsChildCategoryId() != null) {
            categoryList.add(new Category(newsParam.getNewsParentCategoryId()));
        }
        if (newsParam.getNewsChildCategoryId() != null) {
            categoryList.add(new Category(newsParam.getNewsChildCategoryId()));
        }
        news.setCategoryList(categoryList);

        /*添加关键字*/
        /*准备集合装关键字*/
        List<Keyword> keywordList = new ArrayList<>();
        /*接收到的关键字为字符串*/
        String newsKeyword = newsParam.getNewsKeywords();
        if (newsKeyword != null) {
            /*对关键字的空格切割*/
            String[] newsKeywords = newsKeyword.trim().split("\\s+");
            for (String keywordName : newsKeywords) {
                Keyword keyword = new Keyword(keywordName);
                /*添加关键字到数据库*/
                Keyword insertKeyword = keywordService.insertKeyword(keyword);
                /*添加到集合中*/
                keywordList.add(insertKeyword);
            }
        }
        /*设置到News对象属性中*/
        news.setKeywordList(keywordList);

/*        //填充关键字
        List<Keyword> keywordList = new ArrayList<>();
        if (newsParam.getNewsKeywords() != null) {
            for (int i = 0; i < newsParam.getNewsKeywords().size(); i++) {
                Keyword keyword = new Keyword(newsParam.getNewsKeywords().get(i));
                keywordList.add(keyword);
            }
        }
        news.setKeywordList(keywordList);*/

        newsService.insertNews(news);
        return "redirect:/admin";
    }

}



