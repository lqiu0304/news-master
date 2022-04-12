package com.qsl.service;



import com.qsl.entity.Keyword;

import java.util.List;

public interface KeywordService {

	/**
	 * 获得关键字总数
	 *
	 * @return 数量
	 */
	Integer countKeyword() ;

	/**
	 * 获得关键字列表
	 *
	 * @return 关键字列表
	 */
	List<Keyword> listKeyword() ;

	/**
	 * 获得关键字列表
	 *
	 * @return 关键字列表
	 */
	List<Keyword> listKeywordWithCount() ;

	/**
	 * 根据id获得关键字信息
	 *
	 * @param id 关键字ID
	 * @return 关键字
	 */
	Keyword getKeywordById(Integer id) ;

	/**
	 * 添加关键字
	 *
	 * @param keyword 关键字
	 * @return 关键字
	 */
	Keyword insertKeyword(Keyword keyword) ;

	/**
	 * 修改关键字
	 *
	 * @param keyword 关键字
	 */
	void updateKeyword(Keyword keyword) ;

	/**
	 * 删除关键字
	 *
	 * @param id 关键字iD
	 */
    void deleteKeyword(Integer id) ;

	/**
	 * 根据关键字名获取关键字
	 *
	 * @param name 关键字名称
	 * @return 关键字
	 */
	Keyword getKeywordByName(String name) ;

	/**
	 * 根据新闻ID获得关键字
	 *
	 * @param newsId 新闻ID
	 * @return 关键字列表
	 */
	List<Keyword> listKeywordByNewsId(Integer newsId);

}
