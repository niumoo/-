package net.codingme.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.codingme.po.Category;

/** 
 * 导航栏目的业务逻辑接口
 * 
 * @author Niu on 2017年9月6日 下午12:50:22
 */
public interface CategoryService {
	
	// 新增栏目
	public boolean add(HttpServletRequest request);

	// 删除栏目
	public boolean delete(HttpServletRequest request);

	// 更新栏目
	public boolean update(HttpServletRequest request);

	// 查询全部栏目信息
	public List<Category> select(HttpServletRequest request);

}
