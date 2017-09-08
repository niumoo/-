package net.codingme.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.codingme.dao.NewsDao;
import net.codingme.dao.impl.NewsDaoImpl;
import net.codingme.po.News;
import net.codingme.service.NewsService;
import net.codingme.util.StringUtil;

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
	public boolean delete(HttpServletRequest request) {
		return false;
	}
	//更新信息
	@Override
	public boolean update(HttpServletRequest request) {
		return false;
	}
	
	//查询信息
	@Override
	public List<News> select(HttpServletRequest request){
		String newsId = request.getParameter("id");
		
		// 通过信息ID查询
		if(newsId != null){
			String sql = "SELECT * FROM NEWS WHERE NEWS_ID = ? ";
			return newsDao.select(sql, newsId);
		}
		
		// 通过专栏ID查询信息
		String cId = request.getParameter("cId");
		String start = request.getParameter("start");
		String size = request.getParameter("size");
		
		// 通过专栏ID进行分页查询信息
		if(start !=null && size != null && cId != null){
			if(!StringUtil.isNumeric(cId) || !StringUtil.isNumeric(start) 
				|| !StringUtil.isNumeric(size)){
				return new ArrayList<News>();
			}
			String sql = "SELECT * FROM NEWS WHERE C_ID = ? ORDER BY NEWS_CREATE_TIME DESC LIMIT "+start+" , "+size;
			return newsDao.select(sql, cId);
		}
		
		// 通过专栏ID查询所有信息
		if(cId != null){
			String sql = "SELECT * FROM NEWS WHERE C_ID = ? ORDER BY NEWS_CREATE_TIME DESC ";
			return newsDao.select(sql, cId);
		}
		
		// 通过标题关键词进行模糊查询
		String newsTitle = request.getParameter("title");
		if(newsTitle != null){
			if("".equals(newsTitle)){
				return new ArrayList<News>();
			}
			String sql = "SELECT NEWS_ID,NEWS_TITLE FROM NEWS WHERE NEWS_TITLE like ? ORDER BY NEWS_CREATE_TIME DESC LIMIT 5  ";
			return newsDao.likeSelect(sql, newsTitle);
		}
		
		//查询所有信息
		String sql = "SELECT * FROM NEWS ORDER BY NEWS_CREATE_TIME DESC";
		return newsDao.select(sql, null);
	}
	

}
