package net.codingme.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.codingme.po.Category;
import net.codingme.service.CategoryService;
import net.codingme.service.impl.CategoryServiceImpl;
import net.sf.json.JSONArray;

/**
 * restful 形式的导航栏目操作API
 * 
 * @author Niu on 2017年9月6日 下午1:29:34
 */
@WebServlet("/api/category/*")
public class RESTFulCategory extends HttpServlet {
	
	private CategoryService categoryService = new CategoryServiceImpl();

	/**
	 * GET：导航栏目内容获取
	 * /api/category 		获取全部导航栏目
	 * /api/category?id=x	获取id为1的导航栏目
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<Category> categories = categoryService.select(request);
		JSONArray jsonArray = JSONArray.fromObject(categories);
		PrintWriter out = response.getWriter();
		out.print(jsonArray);
		out.close();
	}
	
	/**
	 * POST:新增导航栏目
	 * /api/category	新增一条导航栏目
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		boolean result = categoryService.add(request);
		out.print(result);
	}
	
	/**
	 * PUT：更新导航栏目
	 * /api/category?id=1&cName=x	更新id为1的导航栏目内容为x
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		boolean result = categoryService.update(request);
		out.print(result);
		out.close();
	}
	
	/**
	 * DELETE：删除导航栏目
	 * /api/category?id=1	删除id为1的导航栏目
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		boolean result = categoryService.delete(request);
		out.print(result);
		out.close();
	}
	
	
}
