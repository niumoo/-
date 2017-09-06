package net.codingme.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.codingme.dao.NewsDao;
import net.codingme.dao.impl.NewsDaoImpl;
import net.codingme.po.News;
import net.codingme.service.NewsService;

/** 
 * 信息的业务逻辑处理
 * 
 * @author Niu on 2017年9月5日 下午7:21:58
 */
public class NewsServiceImpl implements NewsService {
	
	private NewsDao newsDao = new NewsDaoImpl(); 

	//新增信息
	@Override
	public boolean add(HttpServletRequest request) {
		return false;
	}
	//删除信息
	@Override
	public boolean delete(Integer newsId) {
		return false;
	}
	//更新信息
	@Override
	public boolean update(HttpServletRequest request) {
		return false;
	}
	//查询信息
	@Override
	public List<News> select() {
		String sql = "SELECT * FROM NEWS";
		List<News> newsList = newsDao.select(sql, null);
		return newsList;
	}
	
	//根据专栏ID查询信息
	@Override
	public List<News> selectByCid(Integer cId) {
		String sql = "SELECT * FROM NEWS WHERE C_ID = ? ";
		List<News> newsList = newsDao.select(sql, cId);
		return newsList;
	}

}
