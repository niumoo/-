package net.codingme.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.codingme.po.User;
import net.codingme.service.UserService;
import net.codingme.service.impl.UserServiceImpl;
import net.sf.json.JSONObject;

/** 
 * 
 * 
 * @author Niu on 2017年9月8日 上午10:24:59
 */
@WebServlet({"/api/login","/api/loginout"})
public class RESTFulLogin extends HttpServlet{
	
	private UserService userService = new UserServiceImpl();

	/*
	 * 用户登陆操作，成功返回用户信息，失败返回空JSON
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		User login = userService.login(request);
		JSONObject jsonObject = JSONObject.fromObject(login);
		out.print(jsonObject);
	}
	
	/*
	 * 登出
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.invalidate();
		resp.getWriter().print("{\"result\":\"true\"}");
	}
}
