package net.codingme.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import net.codingme.dao.CategoryDao;
import net.codingme.dao.impl.CategoryDaoImpl;
import net.codingme.po.Category;
import net.codingme.service.CategoryService;

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
		return false;
	}
	// 删除栏目
	@Override
	public boolean delete(Integer cId) {
		// TODO Auto-generated method stub
		return false;
	}
	// 更新栏目
	@Override
	public boolean update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	// 查询全部栏目信息
	@Override
	public List<Category> select() {
		String sql = "SELECT * FROM CATEGORY";
		return this.categoryDao.select(sql, null);
	}

	// 根据栏目ID查询栏目信息
	@Override
	public List<Category> selectByCid(Integer cId) {
		String sql ="SELEECT * FROM CATEGORY WHERE C_ID = ? ";
		return categoryDao.select(sql, cId);
	}

}
