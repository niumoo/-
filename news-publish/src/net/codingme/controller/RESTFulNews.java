package net.codingme.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.codingme.po.News;
import net.codingme.service.NewsService;
import net.codingme.service.impl.NewsServiceImpl;
import net.sf.json.JSONArray;

/**
 * restful 形式的新闻信息操作API
 * 
 * @author Niu on 2017年9月6日 下午1:29:34
 */
@WebServlet({"/api/news","/api/news/count"})
public class RESTFulNews extends HttpServlet {
	
	private NewsService newsService = new NewsServiceImpl();
	
	/**
	 * GET：新闻内容获取
	 * /api/news 		获取全部信息
	 * /api/news?id=x	获取id为1的信息资源
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String url = request.getRequestURI().replace(request.getContextPath(),"");
		
		if(url.startsWith("/api/news/count")){
			String cId = request.getParameter("cId");
			int count = newsService.selectCount(cId);
			int pageCount=0;
			if(count%8 == 0){
				pageCount = count /8;
			}else{
				pageCount = count /8 + 1;
			}
			String json = "{\"count\":\""+count+"\",\"pageCount\":\""+pageCount+"\"}";
			out.print(json);
			out.close();
			return;
		}
		List<News> news = newsService.select(request);
		JSONArray jsonArray = JSONArray.fromObject(news);
		out.print(jsonArray);
		out.close();
	}

	/**
	 * POST:新增新闻信息
	 * /api/news	新增一条新闻信息
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		String result = newsService.add(request);
		String json = "{\"result\":\""+result+"\"}";
		out.print(json);
		out.close();
	}
	
	/**
	 * PUT：更新新闻信息
	 * /api/news?id=1&内容	更新id为1的新闻信息
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		String result = newsService.update(request);
		String json = "{\"result\":\""+result+"\"}";
		out.print(json);
	}
	
	/**
	 * DELETE：删除新闻信息
	 * /api/news?id=1	删除id为1的新闻信息
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		boolean result = newsService.delete(request);
		out.print(result);
		out.close();
	}
}
