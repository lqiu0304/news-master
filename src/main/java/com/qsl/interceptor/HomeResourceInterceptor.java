package com.qsl.interceptor;

import com.qsl.entity.Category;
import com.qsl.entity.Menu;
import com.qsl.entity.News;
import com.qsl.entity.Options;
import com.qsl.enums.LinkStatus;
import com.qsl.enums.NewsStatus;
import com.qsl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 主菜单拦截器
 */
@Component
public class HomeResourceInterceptor implements HandlerInterceptor {
    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private OptionsService optionsService;

    @Autowired
    private MenuService menuService;

    /**
     * 在请求处理之前执行，该方法主要是用于准备资源数据的，然后可以把它们当做请求属性放到WebRequest中
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws IOException {

        // 菜单显示
        List<Menu> menuList = menuService.listMenu();
        request.setAttribute("menuList", menuList);

        List<Category> categoryList = categoryService.listCategory();
        request.setAttribute("allCategoryList", categoryList);

        //获得网站概况
        List<String> siteBasicStatistics = new ArrayList<String>();
        siteBasicStatistics.add(newsService.countNews(NewsStatus.PUBLISH.getValue()) + "");
        siteBasicStatistics.add(newsService.countNewsComment() + "");
        siteBasicStatistics.add(categoryService.countCategory() + "");
        siteBasicStatistics.add(keywordService.countKeyword() + "");
        siteBasicStatistics.add(linkService.countLink(LinkStatus.NORMAL.getValue()) + "");
        siteBasicStatistics.add(newsService.countNewsView() + "");
        request.setAttribute("siteBasicStatistics", siteBasicStatistics);
        //最后更新的新闻
        News lastUpdateNews = newsService.getLastUpdateNews();
        request.setAttribute("lastUpdateNews", lastUpdateNews);

        //页脚显示
        //新闻基本信息显示(Options)
        Options options = optionsService.getOptions();
        request.setAttribute("options", options);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)  {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e)  {

    }
}