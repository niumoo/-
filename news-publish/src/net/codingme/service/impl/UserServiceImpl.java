package net.codingme.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.valves.rewrite.Substitution.RewriteRuleBackReferenceElement;

import com.sun.java_cup.internal.runtime.virtual_parse_stack;
import com.sun.org.apache.bcel.internal.generic.NEW;

import net.codingme.po.User;
import net.codingme.dao.UserDao;
import net.codingme.dao.impl.UserDaoImpl;
import net.codingme.po.Category;
import net.codingme.po.News;
import net.codingme.service.UserService;
import net.codingme.util.ServletUtil;
import net.codingme.util.StringUtil;

/** 
 * 用户的业务逻辑处理
 * 
 * @author Niu on 2017年9月6日 下午1:04:06
 */
public class UserServiceImpl implements UserService {
	
	private UserDao userDao = new UserDaoImpl();
	
	// 新增用户
	@Override
	public String add(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user.getType() != 1){
			return "只有超级管理员才可以添加用户";
		}
		//获取数据
		String username = request.getParameter("username").trim();
		String useremail = request.getParameter("useremail").trim();
		String password = request.getParameter("password").trim();
		String checkpwd = request.getParameter("checkpwd").trim();
		String usertype = request.getParameter("usertype").trim();

		//值不能为空
		if( username.trim().length()<1 ||
			useremail.trim().length()<1 ||
			password.trim().length()<1 ||
			checkpwd.trim().length()<1 ||
			usertype.trim().length()<1){
			return "信息填写不完整";
		}
		
		//检查用户名是否已经存在
		String sql = "SELECT  \"\" AS PASSWORD ,USER_ID,USER_NAME,USER_EMAIL,MANAGE_CATEGORY,TYPE FROM USER WHERE USER_NAME = ? ";
		List<User> userList = userDao.select(sql, username);
		if(userList.size() >0){
			return "用户名已经存在！";
		}
		
		//检查邮箱是否已经存在
		sql = "SELECT  \"\" AS PASSWORD ,USER_ID,USER_NAME,USER_EMAIL,MANAGE_CATEGORY,TYPE FROM USER WHERE USER_EMAIL = ? ";
		userList = userDao.select(sql, useremail);	
		if(userList.size() >0){
			return "邮箱已经存在";
		}
		
		//新增用户
		sql = "INSERT INTO USER(USER_NAME,USER_EMAIL,PASSWORD,TYPE) VALUES(?,?,?,?)";
		boolean addResult = userDao.add(sql, username,useremail,StringUtil.getMd5(password),usertype);
		if(!addResult){
			return "新增用户失败！";
		}
		return "true";
	}

	// 删除用户
	@Override
	public boolean delete(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		//超级管理员才允许的操作
		if(user.getType() != 1){
			return false;
		}
		Map<String, String> urlToMap = ServletUtil.urlToMap(request);
		String userId = urlToMap.get("userId");
		if("".equals(userId)){
			return false;
		}
		String sql = "DELETE FROM USER WHERE USER_ID= ?";
		return userDao.delete(sql, userId);
	}

	//更新用户
	@Override
	public String update(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Map<String, String> urlToMap = ServletUtil.urlToMap(request);
		
		//修改密码
		if(urlToMap.get("oldPassword") !=null){
			String oldPassword = urlToMap.get("oldPassword").trim();
			String newPassword = urlToMap.get("newPassword").trim();
			Integer userId = user.getUserId();
			
			//检查旧密码是否正确
			String sql = "SELECT  \"\" AS PASSWORD ,USER_ID,USER_NAME,USER_EMAIL,MANAGE_CATEGORY,TYPE FROM USER WHERE PASSWORD = ? ";
			List<User> userList = userDao.select(sql, StringUtil.getMd5(oldPassword));
			if(userList.size()<1 || userList.get(0).getUserId() != userId){
				return "旧密码输入不正确！";
			}
			
			//更新密码
			sql = "UPDATE USER SET PASSWORD =  ? WHERE USER_ID = ?";
			boolean update = userDao.update(sql, StringUtil.getMd5(newPassword),user.getUserId());
			if(!update){
				return "密码修改失败";
			}
			return "true";
		}
		
		//获取数据
		String userId = urlToMap.get("userId").trim();
		String username = urlToMap.get("username").trim();
		String useremail = urlToMap.get("useremail").trim();
		String usertype = urlToMap.get("usertype").trim();		
		
		//普通用户只能更新自己信息
		if(user.getType() == 2 && !userId.equals(user.getUserId())){
			return "你的操作超出了你的权限！";
		}
		
		//值不能为空
		if( userId.trim().length()<1 ||
			username.trim().length()<1 ||
			useremail.trim().length()<1 ||
			usertype.trim().length()<1){
			return "信息填写不完整";
		}
		
		//检查用户昵称是否已经存在
		String sql = "SELECT  \"\" AS PASSWORD ,USER_ID,USER_NAME,USER_EMAIL,MANAGE_CATEGORY,TYPE FROM USER WHERE USER_NAME = ? ";
		List<User> userList = userDao.select(sql, username);
		if(userList.size() >0){
			Integer selecId = userList.get(0).getUserId();
			if(!userId.equals(selecId+"")){
				return "用户名已经存在！";
			}
		}
		
		//检查邮箱是否已经存在
		sql = "SELECT  \"\" AS PASSWORD ,USER_ID,USER_NAME,USER_EMAIL,MANAGE_CATEGORY,TYPE FROM USER WHERE USER_EMAIL = ? ";
		userList = userDao.select(sql, useremail);	
		if(userList.size() >0){
			Integer selecId = userList.get(0).getUserId();
			if(!userId.equals(selecId+"")){
				return "邮箱已经存在";
			}
		}
		
		//更新信息
		sql = "UPDATE USER SET USER_NAME = ? , USER_EMAIL= ? , TYPE = ? WHERE USER_ID = ?";
		boolean updateResult = userDao.update(sql, username,useremail,usertype,userId);
		if(!updateResult){
			return "更新用户信息失败！";
		}
		return "true";
	}

	// 查询用户信息，不显示敏感信息
	@Override
	public List<User> select(HttpServletRequest request){
		String userId = request.getParameter("id");
		
		//根据用户ID查询
		if(userId != null){
			String sql = "SELECT  \"\" AS PASSWORD ,USER_ID,USER_NAME,USER_EMAIL,MANAGE_CATEGORY,TYPE FROM USER WHERE USER_ID = ? ";
			return userDao.select(sql, userId);
		}
		
		//根据栏目ID查询
		String cId = request.getParameter("categoryId");
		if(cId != null){
			String sql = "SELECT  \"\" AS PASSWORD ,USER_ID,USER_NAME,USER_EMAIL,MANAGE_CATEGORY,TYPE FROM USER WHERE MANAGE_CATEGORY = ? ";
			return userDao.select(sql, cId);
		}
		
		//根据用户昵称查询
		String username = request.getParameter("username");
		if(username != null){
			String sql = "SELECT  \"\" AS PASSWORD ,USER_ID,USER_NAME,USER_EMAIL,MANAGE_CATEGORY,TYPE FROM USER WHERE USER_NAME = ? ";
			return userDao.select(sql, username);
		}
		
		//根据用户邮箱查询
		String email = request.getParameter("email");
		if(email !=null){
			String sql = "SELECT  \"\" AS PASSWORD ,USER_ID,USER_NAME,USER_EMAIL,MANAGE_CATEGORY,TYPE FROM USER WHERE USER_EMAIL = ? ";
			return userDao.select(sql, email);	
		}
		
		String sql = "SELECT \"\" AS PASSWORD ,USER_ID,USER_NAME,USER_EMAIL,MANAGE_CATEGORY,TYPE FROM USER";
		return userDao.select(sql, null);
	}

	// 登陆用户
	@Override
	public User login(HttpServletRequest request) {
		String userEmail = request.getParameter("email");
		String password = request.getParameter("password");
		String sql = "SELECT * FROM USER WHERE USER_EMAIL = ? AND PASSWORD = ? ";
		System.out.println(userEmail);
		System.out.println(StringUtil.getMd5(password));
		List<User> users = userDao.select(sql,userEmail,StringUtil.getMd5(password) );
		if(users!=null && users.size() >0){
			User user = users.get(0);
			user.setPassword("");
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			return user;
		}
		return null;
	}
	
	/**
	 * 检查权限 普通用户不能进行news的非自我管理的POST,PUT,DELET操作
	 * @param request
	 * @param newsId
	 * @return
	 */
	public boolean checkAuthority(HttpServletRequest request ,String userId){
		User user = (User) request.getSession().getAttribute("user");
		if(user.getType() == 1){
			return true;
		}
		if(user.getType() == 2){
			if( user.getUserId().toString().equals(userId)){
				return true;
			}
		}
		return false;
	}
	
}
