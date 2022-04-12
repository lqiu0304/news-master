package com.qsl.controller.home;


import com.qsl.entity.Link;
import com.qsl.entity.News;
import com.qsl.enums.LinkStatus;
import com.qsl.service.LinkService;
import com.qsl.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;


@Controller
public class LinkController {
	@Autowired
	private LinkService linkService;

	@Autowired
	private NewsService newsService;

	@RequestMapping("/applyLink")
	public String applyLinkView(Model model)  {
		//侧边栏显示
		//获得热评新闻
		List<News> mostCommentNewsList = newsService.listNewsByCommentCount(8);
		model.addAttribute("mostCommentNewsList", mostCommentNewsList);
		return "Home/Page/applyLink";
	}


	@RequestMapping(value = "/applyLinkSubmit",method = {RequestMethod.POST}, produces = {"text/plain;charset=UTF-8"})
	@ResponseBody
	public void applyLinkSubmit(Link link)  {
		link.setLinkStatus(LinkStatus.HIDDEN.getValue());
		link.setLinkCreateTime(new Date());
		link.setLinkUpdateTime(new Date());
		linkService.insertLink(link);
	}
}
