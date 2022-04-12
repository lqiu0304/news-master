package com.qsl.service.impl;

import com.qsl.entity.User;
import com.qsl.mapper.CommentMapper;
import com.qsl.mapper.NewsMapper;
import com.qsl.mapper.UserMapper;
import com.qsl.service.NewsService;
import com.qsl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsService newsService;
    @Autowired
    private CommentMapper commentMapper;


    /**获取用户列表*/
    @Override
    public List<User> listUser() {
        List<User> userList = userMapper.listUser();
        for (int i = 0; i < userList.size(); i++) {
            Integer newsCount = newsMapper.countNewsByUser(userList.get(i).getUserId());
            userList.get(i).setNewsCount(newsCount);
        }
        return userList;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.update(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer id) {
        // 删除用户
        userMapper.deleteById(id);
        // 删除评论
        commentMapper.deleteByUserId(id);
        // 删除新闻
        List<Integer> newsIds = newsMapper.listNewsIdsByUserId(id);
        if (newsIds != null && newsIds.size() > 0) {
            for (Integer newsId : newsIds) {
                newsService.deleteNews(newsId);
            }
        }
    }

    @Override
    public User insertUser(User user) {
        user.setUserRegisterTime(new Date());
        userMapper.insert(user);
        return user;
    }

    @Override
    public User getUserByNameOrEmail(String str) {
        return userMapper.getUserByNameOrEmail(str);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }


}
