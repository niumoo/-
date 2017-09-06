package net.codingme.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.codingme.po.Admin;
import net.codingme.po.Category;
import net.codingme.service.AdminService;

/** 
 * 用户的业务逻辑处理
 * 
 * @author Niu on 2017年9月6日 下午1:04:06
 */
public class AdminServiceImpl implements AdminService {
	
	// 新增用户
	@Override
	public boolean add(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	// 删除用户
	@Override
	public boolean delete(Integer userId) {
		// TODO Auto-generated method stub
		return false;
	}

	//更新用户
	@Override
	public boolean update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	// 查询全部用户信息
	@Override
	public List<Category> select() {
		// TODO Auto-generated method stub
		return null;
	}

	// 根据用户ID查询用户信息
	@Override
	public List<Category> selectByCid(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 登陆用户
	@Override
	public Admin login(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
