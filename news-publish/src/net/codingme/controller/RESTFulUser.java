package net.codingme.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.codingme.po.User;
import net.codingme.service.UserService;
import net.codingme.service.impl.UserServiceImpl;
import net.sf.json.JSONArray;

/** 
 * restful 形式的用户操作API
 * 
 * @author Niu on 2017年9月6日 下午7:06:42
 */
@WebServlet("/api/user/*")
public class RESTFulUser extends HttpServlet {	
	
	private UserService userService = new UserServiceImpl();
	
	/**
	 * GET：用户获取
	 * /api/user 		获取全部信息
	 * /api/user?id=x	获取id为1的用户信息
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> news = userService.select(request);
		JSONArray jsonArray = JSONArray.fromObject(news);
		PrintWriter out = response.getWriter();
		out.print(jsonArray);
		out.close();
	}
	
	/**
	 * POST:新增用户
	 * /api/user	新增一条用户
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		boolean result = userService.add(request);
		out.print(result);
		out.close();
	}
	
	/**
	 * PUT：更新用户
	 * /api/user?id=1	更新id为1的用户
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		boolean result = userService.update(request);
		out.print(result);
		out.close();
	}
	
	/**
	 * DELETE：删除用户
	 * /api/user?id=1	删除id为1的用户
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		boolean result = userService.delete(request);
		out.print(result);
		out.close();
	}
	
}
