package com.qsl.controller.home;

import cn.hutool.http.HtmlUtil;

import com.qsl.dto.JsonResult;
import com.qsl.entity.Comment;
import com.qsl.entity.News;
import com.qsl.entity.User;
import com.qsl.enums.NewsStatus;
import com.qsl.enums.Role;
import com.qsl.service.CommentService;
import com.qsl.service.NewsService;
import com.qsl.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;



@Controller
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private NewsService newsService;

    /**
     * 添加评论
     *
     * @param request
     * @param comment
     */
    @RequestMapping(value = "/comment", method = {RequestMethod.POST})
    public JsonResult insertComment(HttpServletRequest request, Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new JsonResult().fail("请先登录");
        }
        News news = newsService.getNewsByStatusAndId(NewsStatus.PUBLISH.getValue(), comment.getCommentNewsId());
        if (news == null) {
            return new JsonResult().fail("新闻不存在");
        }


        //添加评论
        comment.setCommentUserId(user.getUserId());//设置用户id
        comment.setCommentCreateTime(new Date());
        comment.setCommentIp(MyUtils.getIpAddr(request));
        /*判断是否是作者本人*/
        if (Objects.equals(user.getUserId(), news.getNewsUserId())) {
            comment.setCommentRole(Role.OWNER.getValue());
        } else {
            comment.setCommentRole(Role.VISITOR.getValue());
        }
        comment.setCommentAuthorAvatar(user.getUserAvatar());

        //过滤字符，防止XSS攻击
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));

        comment.setCommentAuthorName(user.getUserNickname());
        comment.setCommentAuthorEmail(user.getUserEmail());
        comment.setCommentAuthorUrl(user.getUserUrl());
        try {
            commentService.insertComment(comment);
            //更新新闻的评论数
            newsService.updateCommentCount(news.getNewsId());
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().fail();
        }
        return new JsonResult().ok();
    }


}
