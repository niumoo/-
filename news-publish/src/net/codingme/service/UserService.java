package net.codingme.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.codingme.po.User;
import net.codingme.po.Category;

/** 
 * 用户的业务逻辑接口
 * 
 * @author Niu on 2017年9月6日 下午12:54:01
 */
public interface UserService {
	
	// 新增用户
	public boolean add(HttpServletRequest request);

	// 删除用户
	public boolean delete(HttpServletRequest request);

	// 更新用户
	public boolean update(HttpServletRequest request);

	// 查询全部用户信息
	public List<User> select(HttpServletRequest request);

	// 登陆用户
	public User login(HttpServletRequest request);
	
	
}
