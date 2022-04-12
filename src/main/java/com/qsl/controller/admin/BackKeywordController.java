package com.qsl.controller.admin;



import com.qsl.entity.Keyword;
import com.qsl.service.KeywordService;
import com.qsl.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;



@Controller
@RequestMapping("/admin/keyword")
public class BackKeywordController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private KeywordService keywordService;

    /**
     * 后台关键字列表显示
     * @return
     */
    @RequestMapping(value = "")
    public ModelAndView index()  {
        ModelAndView modelandview = new ModelAndView();
        List<Keyword> keywordList = keywordService.listKeywordWithCount();
        modelandview.addObject("keywordList",keywordList);

        modelandview.setViewName("Admin/Keyword/index");
        return modelandview;

    }


    /**
     * 后台添加关键字页面显示
     *
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    public String insertKeywordSubmit(Keyword keyword)  {
        keywordService.insertKeyword(keyword);
        return "redirect:/admin/keyword";
    }

    /**
     * 删除关键字
     *
     * @param id 关键字ID
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String deleteKeyword(@PathVariable("id") Integer id)  {
        Integer count = newsService.countNewsByKeywordId(id);
        if (count == 0) {
            keywordService.deleteKeyword(id);
        }
        return "redirect:/admin/keyword";
    }

    /**
     * 编辑关键字页面显示
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editKeywordView(@PathVariable("id") Integer id)  {
        ModelAndView modelAndView = new ModelAndView();

        Keyword keyword =  keywordService.getKeywordById(id);
        modelAndView.addObject("keyword",keyword);

        List<Keyword> keywordList = keywordService.listKeywordWithCount();
        modelAndView.addObject("keywordList",keywordList);

        modelAndView.setViewName("Admin/Keyword/edit");
        return modelAndView;
    }


    /**
     * 编辑关键字提交
     *
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editKeywordSubmit(Keyword keyword)  {
        keywordService.updateKeyword(keyword);
        return "redirect:/admin/keyword";
    }
}
