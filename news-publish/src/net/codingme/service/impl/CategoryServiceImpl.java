package net.codingme.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jcp.xml.dsig.internal.MacOutputStream;

import net.codingme.dao.CategoryDao;
import net.codingme.dao.NewsDao;
import net.codingme.dao.UserDao;
import net.codingme.dao.impl.CategoryDaoImpl;
import net.codingme.dao.impl.NewsDaoImpl;
import net.codingme.dao.impl.UserDaoImpl;
import net.codingme.po.Category;
import net.codingme.po.News;
import net.codingme.service.CategoryService;
import net.codingme.util.ServletUtil;

/** 
 * 导航栏目的业务逻辑处理
 * 
 * @author Niu on 2017年9月6日 下午12:59:58
 */
public class CategoryServiceImpl implements CategoryService {

	private CategoryDao categoryDao = new CategoryDaoImpl();
	private NewsDao newsDao = new NewsDaoImpl();
	private UserDao userDao = new UserDaoImpl();
	
	// 新增栏目
	@Override
	public boolean add(HttpServletRequest request) {
		String cName = request.getParameter("cName");
		String userId = request.getParameter("userId");
		if(cName == null || cName.trim().length() < 1){
			return false;
		}
		
		//检查是否已经存在
		String sql ="SELECT * FROM CATEGORY WHERE C_NAME = ? ";
		List<Category> categories = categoryDao.select(sql, cName);
		if(categories.size()>0){
			return false;
		}
		
		//新增栏目
		sql = "INSERT INTO CATEGORY(C_NAME) VALUE(?)";
		boolean addResult = categoryDao.add(sql, cName);
		if(!addResult){
			return false;
		}
		
		//是否设置了管理员
		if(!"null".equals(userId)){
			sql = "SELECT * FROM CATEGORY WHERE C_NAME = ?";
			List<Category> categorieList = categoryDao.select(sql, cName);
			Integer cId = categorieList.get(0).getcId();
			sql = "UPDATE USER SET MANAGE_CATEGORY = ? WHERE USER_ID = ?";
			boolean updateResult = userDao.update(sql, cId,userId);
			if(!updateResult){
				return false;
			}
		}
		return true;
	}
	
	// 删除栏目
	@Override
	public boolean delete(HttpServletRequest request) {
		Map<String, String> map = ServletUtil.urlToMap(request);
		Integer cId = Integer.parseInt(map.get("id"));
		if(cId == null ){
			return false;
		}
		
		//判断是否有文章信息
		String sql = "SELECT * FROM NEWS WHERE C_ID = ? ";
		List<News> newsList = newsDao.select(sql, cId);
		if(newsList.size() > 0){
			return false;
		}
		
		//检查是否有栏目管理员，有则更新管理员管理栏目信息
		sql = "UPDATE USER SET MANAGE_CATEGORY='' WHERE MANAGE_CATEGORY= ?";
		userDao.update(sql, cId);
		sql = "DELETE FROM CATEGORY WHERE C_ID = ?";
		boolean deleteResult = categoryDao.delete(sql, cId);
		return deleteResult;
	}
	
	// 更新栏目
	@Override
	public boolean update(HttpServletRequest request) {
		Map<String, String> map = ServletUtil.urlToMap(request);
		String cId = map.get("cId");
		String cName = map.get("cName");
		String userId = map.get("userId");
		if (cId == null || cName == null ||
			cName.trim().length()<1 || cId.trim().length()<1) {
			return false;
		}

		// 检查是栏目是否已经存在
		String sql = "SELECT * FROM CATEGORY WHERE C_NAME = ? ";
		List<Category> categories = categoryDao.select(sql, cName);
		if (categories.size() !=0) {
			Integer getcId = categories.get(0).getcId();
			if(Integer.parseInt(cId) != getcId){
				System.out.println("检查是栏目是否已经存在不通过");
				return false;
			}
		}
		
		//更新栏目
		sql = "UPDATE CATEGORY SET C_NAME = ? WHERE C_ID = ?";
		boolean updateResult = categoryDao.update(sql, cName, cId);
		if (!updateResult) {
			System.out.println("更新栏目不通过");
			return false;
		}
		
		// 清空更新之前的栏目管理员信息
		sql = "UPDATE USER SET MANAGE_CATEGORY='' WHERE MANAGE_CATEGORY= ?";
		userDao.update(sql, cId);
		
		// 是否指定了管理员，指定则更新，没有则跳过
		if (!"null".equals(userId)) {
			sql = "UPDATE USER SET MANAGE_CATEGORY = ? WHERE USER_ID = ?";
			updateResult = userDao.update(sql, cId, userId);
			if (!updateResult) {
				System.out.println("是否指定了管理员，指定则更新，没有则跳过不通过");
				return false;
			}
		}
		return true;
	}

	// 查询全部栏目信息
	@Override
	public List<Category> select(HttpServletRequest request) {
		String cId = request.getParameter("id");
		if (cId != null) {
			String sql ="SELECT * FROM CATEGORY WHERE C_ID = ? ";
			return categoryDao.select(sql, cId);
		}
		String sql = "SELECT * FROM CATEGORY";
		return this.categoryDao.select(sql, null);
	}

}
