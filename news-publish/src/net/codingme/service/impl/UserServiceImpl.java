package net.codingme.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sun.org.apache.bcel.internal.generic.NEW;

import net.codingme.po.User;
import net.codingme.dao.UserDao;
import net.codingme.dao.impl.UserDaoImpl;
import net.codingme.po.Category;
import net.codingme.service.UserService;

/** 
 * 用户的业务逻辑处理
 * 
 * @author Niu on 2017年9月6日 下午1:04:06
 */
public class UserServiceImpl implements UserService {
	
	private UserDao userDao = new UserDaoImpl();
	
	// 新增用户
	@Override
	public boolean add(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	// 删除用户
	@Override
	public boolean delete(HttpServletRequest request) {
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
	public List<User> select(HttpServletRequest request){
		String userId = request.getParameter("id");
		if(userId!=null){
			String sql = "SELECT * FROM USER WHERE USER_ID = ? ";
			return userDao.select(sql, userId);
		}
		String sql = "SELECT * FROM USER";
		return userDao.select(sql, null);
	}

	// 登陆用户
	@Override
	public User login(HttpServletRequest request) {
		return null;
	}
	
	
}
