package net.codingme.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.valves.rewrite.Substitution.RewriteRuleBackReferenceElement;

import com.mysql.jdbc.ResultSetInternalMethods;

import net.codingme.dao.NewsDao;
import net.codingme.dao.impl.NewsDaoImpl;
import net.codingme.po.News;
import net.codingme.po.User;
import net.codingme.service.NewsService;
import net.codingme.util.ServletUtil;
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
	public String add(HttpServletRequest request) {
		//获取参数
		String title = request.getParameter("title").trim();
		String content = request.getParameter("content").trim();
		String cId = request.getParameter("cId").trim();
		String file1Path = request.getParameter("file1Path").trim();
		String file2Path = request.getParameter("file2Path").trim();
		String file3Path = request.getParameter("file3Path").trim();
		
		//检查权限
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user.getType() == 2 && !cId.equals(user.getManegeCategory())){
			return "你的操作超出了你的权限！";
		}
		
		//检查是否为空
		if(title.isEmpty() || content.isEmpty() || cId.isEmpty()){
			return "数据填写不完整！";
		}
		Date now = new Date();
		String sql = "INSERT INTO NEWS(NEWS_TITLE,NEWS_CONTENT,NEWS_CREATE_TIME,NEWS_FILE1,NEWS_FILE2,NEWS_FILE3,C_ID)"
				   + "VALUES(?,?,?,?,?,?,?)";
		boolean addResult = newsDao.add(sql, title,content,now,file1Path,file2Path,file3Path,cId);
		if(!addResult){
			return "发布文章失败";
		}
		return "true";
	}
	
	//删除信息
	@Override
	public boolean delete(HttpServletRequest request) {
		Map<String, String> urlToMap = ServletUtil.urlToMap(request);
		String newsId = urlToMap.get("id");
		if(newsId == null){
			return false;
		}
		if(checkAuthority(request, newsId)){
			String sql ="DELETE FROM NEWS WHERE NEWS_ID = ?";
			return newsDao.delete(sql, newsId);
		}
		return false;
	}
	
	//更新信息
	@Override
	public String update(HttpServletRequest request) {
		Map<String, String> urlToMap = ServletUtil.urlToMap(request);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		//批量转移
		if(urlToMap.get("selectNewsId") != null){
			if(user.getType() != 1){
				return "你的操作超出了你的权限";
			}
			String[] selectNewsId = urlToMap.get("selectNewsId").split(",");
			String cId = urlToMap.get("cId");
			for (String newsId : selectNewsId) {
				String sql = "UPDATE NEWS SET C_ID = ? WHERE NEWS_ID = ?";
				newsDao.update(sql, cId,newsId);
			};
			return "true";
		}
		// 获取参数
		String newsId = urlToMap.get("newsId").trim();
		String title = urlToMap.get("title").trim();
		String content = urlToMap.get("content").trim();
		String cId = urlToMap.get("cId").trim();
		String file1Path = null;
		if(urlToMap.get("file1Path") != null){
			file1Path = urlToMap.get("file1Path").trim();
		}
		String file2Path =  null;
		if(urlToMap.get("file2Path") != null){
			file2Path = urlToMap.get("file2Path").trim();
		}
		String file3Path =  null;
		if(urlToMap.get("file3Path") != null){
			file3Path = urlToMap.get("file3Path").trim();
		}

		// 检查权限
		if (user.getType() == 2 && !cId.equals(user.getManegeCategory())) {
			return "你的操作超出了你的权限！";
		}

		// 检查是否为空
		if (title.isEmpty() || content.isEmpty() || cId.isEmpty() || newsId.isEmpty()) {
			return "数据填写不完整！";
		}
		Date now = new Date();
		String sql = "UPDATE NEWS SET NEWS_TITLE = ? ,NEWS_CONTENT = ?,NEWS_CREATE_TIME = ?,"
				+ "NEWS_FILE1 = ?,NEWS_FILE2 = ?,NEWS_FILE3 = ?,C_ID = ? WHERE NEWS_ID = ?";
		boolean addResult = newsDao.add(sql, title, content, now, file1Path, file2Path, file3Path, cId,newsId);
		if (!addResult) {
			return "文章更新失败";
		}
		return "true";
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
		if(start !=null && size != null){
			if(!StringUtil.isNumeric(start) || !StringUtil.isNumeric(size)){
				return null;
			}
			//专栏分页查询
			if( cId != null){
				String sql = "SELECT * FROM NEWS WHERE C_ID = ? ORDER BY NEWS_CREATE_TIME DESC LIMIT "+start+" , "+size;
				return newsDao.select(sql, cId);
			}
			//全部分页查询
			if( cId == null){
				String sql = "SELECT * FROM NEWS ORDER BY NEWS_CREATE_TIME DESC LIMIT "+start+" , "+size;
				return newsDao.select(sql, null);
			}
			
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
	
	/**
	 * 检查权限 普通用户不能进行news的非自我管理的POST,PUT,DELET操作
	 * @param request
	 * @param newsId
	 * @return
	 */
	public boolean checkAuthority(HttpServletRequest request ,String newsId){
		User user = (User) request.getSession().getAttribute("user");
		if(user.getType() == 1){
			return true;
		}
		if(user.getType() == 2){
			// 检查是不是自己管理的栏目信息
			String sql = "SELECT * FROM NEWS WHERE NEWS_ID = ? ";
			List<News> newsList = newsDao.select(sql, newsId);
			Integer getcId = newsList.get(0).getcId();
			if(user.getManegeCategory().contains(getcId+"")){
				return true;
			}
		}
		return false;
	}

	/**
	 * 查询记录总共有多少条
	 * @return
	 */
	@Override
	public int selectCount(String cId) {
		if(cId != null){
			String sql = "SELECT COUNT(*) AS NEWS_ID,NEWS_TITLE,NEWS_CONTENT,NEWS_CREATE_TIME,"
					+ "NEWS_FILE1,NEWS_FILE2,NEWS_FILE3,C_ID FROM NEWS WHERE C_ID = ? ";
			List<News> likeSelect = newsDao.select(sql,cId);
			return likeSelect.get(0).getNewsId();
		}
		String sql = "SELECT COUNT(*) AS NEWS_ID,NEWS_TITLE,NEWS_CONTENT,NEWS_CREATE_TIME,"
				+ "NEWS_FILE1,NEWS_FILE2,NEWS_FILE3,C_ID FROM NEWS";
		List<News> likeSelect = newsDao.select(sql,null);
		return likeSelect.get(0).getNewsId();
	}
	

}
