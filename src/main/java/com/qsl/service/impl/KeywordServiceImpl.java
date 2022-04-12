package com.qsl.service.impl;


import com.qsl.entity.Keyword;
import com.qsl.mapper.KeywordMapper;
import com.qsl.mapper.NewsKeywordRefMapper;
import com.qsl.service.KeywordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
@Slf4j
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordMapper keywordMapper;

    @Autowired
    private NewsKeywordRefMapper newsKeywordRefMapper;

    @Override
    public Integer countKeyword() {
        return keywordMapper.countKeyword();
    }

    @Override
    public List<Keyword> listKeyword() {
        List<Keyword> keywordList = null;
        try {
            keywordList = keywordMapper.listKeyword();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得所有关键字失败, cause:{}", e);
        }
        return keywordList;
    }

    @Override
    public List<Keyword> listKeywordWithCount() {
        List<Keyword> keywordList = null;
        try {
            keywordList = keywordMapper.listKeyword();
            for (int i = 0; i < keywordList.size(); i++) {
                Integer count = newsKeywordRefMapper.countNewsByKeywordId(keywordList.get(i).getKeywordId());
                keywordList.get(i).setNewsCount(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得所有关键字失败, cause:{}", e);
        }
        return keywordList;
    }


    @Override
    public Keyword getKeywordById(Integer id) {
        Keyword keyword = null;
        try {
            keyword = keywordMapper.getKeywordById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据ID获得关键字失败, id:{}, cause:{}", id, e);
        }
        return keyword;
    }

    @Override
    public Keyword insertKeyword(Keyword keyword) {
        try {
            if (getKeywordByName(keyword.getKeywordName()) == null) {
                keywordMapper.insert(keyword);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加关键字失败, keyword:{}, cause:{}", keyword, e);
        }

        return getKeywordByName(keyword.getKeywordName());
    }


    @Override
    public void updateKeyword(Keyword keyword) {
        try {
            keywordMapper.update(keyword);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新关键字失败, keyword:{}, cause:{}", keyword, e);
        }
    }

    /**
     * 根据id删除关键字
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteKeyword(Integer id) {
        try {
            keywordMapper.deleteById(id);
            newsKeywordRefMapper.deleteByKeywordId(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除关键字失败, id:{}, cause:{}", id, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

    }

    /**
     * 根据名称获取关键字
     */
    @Override
    public Keyword getKeywordByName(String name) {
        Keyword keyword = null;
        try {
            keyword = keywordMapper.getKeywordByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据名称获得关键字, name:{}, cause:{}", name, e);
        }
        return keyword;
    }

    /**
     * 根据新闻id获取关键字
     */
    @Override
    public List<Keyword> listKeywordByNewsId(Integer newsId) {
        List<Keyword> keywordList = null;
        try {
            keywordList = newsKeywordRefMapper.listKeywordByNewsId(newsId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据新闻ID获得关键字失败，newsId:{}, cause:{}", newsId, e);
        }
        return keywordList;
    }


}
