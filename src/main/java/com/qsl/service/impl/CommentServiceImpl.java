package com.qsl.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qsl.entity.Comment;
import com.qsl.entity.News;
import com.qsl.enums.NewsStatus;
import com.qsl.mapper.CommentMapper;
import com.qsl.mapper.NewsMapper;
import com.qsl.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public void insertComment(Comment comment) {
        try {
            commentMapper.insert(comment);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建评论失败：comment:{}, cause:{}", comment, e);
        }
    }

    @Override
    public List<Comment> listCommentByNewsId(Integer newsId) {
        List<Comment> commentList = null;
        try {
            commentList = commentMapper.listCommentByNewsId(newsId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据新闻ID获得评论列表失败，newsId:{},cause:{}", newsId, e);
        }
        return commentList;
    }

    @Override
    public Comment getCommentById(Integer id) {
        Comment comment = null;
        try {
            comment = commentMapper.getCommentById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据评论ID获得评论，id:{}, cause:{}", id, e);
        }
        return comment;
    }

    @Override
    public PageInfo<Comment> listCommentByPage(Integer pageIndex, Integer pageSize, HashMap<String, Object> criteria) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Comment> commentList = null;
        try {
            commentList = commentMapper.listComment(criteria);
            for (int i = 0; i < commentList.size(); i++) {
                News news = newsMapper.getNewsByStatusAndId(NewsStatus.PUBLISH.getValue(), commentList.get(i).getCommentNewsId());
                commentList.get(i).setNews(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("分页获得评论失败,pageIndex:{}, pageSize:{}, cause:{}", pageIndex, pageSize, e);
        }
        return new PageInfo<>(commentList);
    }

    @Override
    public PageInfo<Comment> listReceiveCommentByPage(Integer pageIndex, Integer pageSize, Integer userId) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Comment> commentList = new ArrayList<>();
        try {
            List<Integer> newsIds = newsMapper.listNewsIdsByUserId(userId);
            if (newsIds != null && newsIds.size() > 0) {
                commentList = commentMapper.getReceiveComment(newsIds);
                for (int i = 0; i < commentList.size(); i++) {
                    News news = newsMapper.getNewsByStatusAndId(NewsStatus.PUBLISH.getValue(), commentList.get(i).getCommentNewsId());
                    commentList.get(i).setNews(news);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("分页获得评论失败,pageIndex:{}, pageSize:{}, cause:{}", pageIndex, pageSize, e);
        }
        return new PageInfo<>(commentList);
    }

    @Override
    public void deleteComment(Integer id) {
        try {
            commentMapper.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除评论失败, id:{}, cause:{}", id, e);
        }
    }

    @Override
    public void updateComment(Comment comment) {
        try {
            commentMapper.update(comment);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新评论，comment:{}, cause:{}", comment, e);
        }
    }

    @Override
    public Integer countComment() {
        Integer commentCount = null;
        try {
            commentCount = commentMapper.countComment();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计评论数量失败, cause:{}", e);
        }
        return commentCount;
    }

    @Override
    public List<Comment> listRecentComment(Integer userId, Integer limit) {
        List<Comment> commentList = null;
        try {
            commentList = commentMapper.listRecentComment(userId, limit);
            for (int i = 0; i < commentList.size(); i++) {
                News news = newsMapper.getNewsByStatusAndId(NewsStatus.PUBLISH.getValue(), commentList.get(i).getCommentNewsId());
                commentList.get(i).setNews(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得最新评论失败, limit:{}, cause:{}", limit, e);
        }
        return commentList;
    }

    @Override
    public List<Comment> listChildComment(Integer id) {
        List<Comment> childCommentList = null;
        try {
            childCommentList = commentMapper.listChildComment(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得子评论失败, id:{}, cause:{}", id, e);
        }
        return childCommentList;
    }

}
