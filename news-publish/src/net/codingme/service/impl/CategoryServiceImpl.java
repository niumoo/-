package net.codingme.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jcp.xml.dsig.internal.MacOutputStream;

import net.codingme.dao.CategoryDao;
import net.codingme.dao.impl.CategoryDaoImpl;
import net.codingme.po.Category;
import net.codingme.service.CategoryService;
import net.codingme.util.ServletUtil;

/** 
 * 导航栏目的业务逻辑处理
 * 
 * @author Niu on 2017年9月6日 下午12:59:58
 */
public class CategoryServiceImpl implements CategoryService {

	private CategoryDao categoryDao = new CategoryDaoImpl();
	
	// 新增栏目
	@Override
	public boolean add(HttpServletRequest request) {
		String cId = request.getParameter("id");
		String cName = request.getParameter("cName");
		String sql = "INSERT INTO CATEGORY(C_NAME) VALUE(?)";
		return categoryDao.add(sql, cName);
	}
	// 删除栏目
	@Override
	public boolean delete(HttpServletRequest request) {
		Map<String, String> map = ServletUtil.urlToMap(request);
		Integer cId = Integer.parseInt(map.get("id"));
		if(cId == null ){
			return false;
		}
		String sql = "DELETE FROM CATEGORY WHERE C_ID = ?";
		return categoryDao.delete(sql, cId);
	}
	// 更新栏目
	@Override
	public boolean update(HttpServletRequest request) {
		Map<String, String> map = ServletUtil.urlToMap(request);
		String cId = map.get("id");
		String cName = map.get("cName");
		String sql = "UPDATE CATEGORY SET C_NAME = ? WHERE C_ID = ?";
		return categoryDao.update(sql, cName,cId);
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
