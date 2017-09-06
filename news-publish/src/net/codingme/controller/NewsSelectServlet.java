package net.codingme.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.codingme.po.News;
import net.codingme.service.NewsService;
import net.codingme.service.impl.NewsServiceImpl;

/**
 * 查询所有信息
 * @author Niu
 */
@WebServlet("/newsSelect")
public class NewsSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private NewsService newsService = new NewsServiceImpl();
	
	//查询所有信息内容
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<News> newsList = newsService.select();
		request.setAttribute("newsList", newsList);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
