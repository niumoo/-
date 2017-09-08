package net.codingme.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 过滤编码
 * 过滤权限
 * 
 * @author Niu on 2017年9月6日 下午14:25:08
 */
@WebFilter("/*")
public class FilterGeneral implements Filter {
	
	// 过滤编码格式
	private final String encode="utf-8";
	// 需要权限的请求方法
	private final List<String> methods=new ArrayList<String>();
	// 需要权限的路径
	private final String[] adminPath={"/api/user"};

	public FilterGeneral() {
		methods.add("POST"); 
		methods.add("PUT");
		methods.add("DELETE");
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//类型转换
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		// 设置编码
		req.setCharacterEncoding(encode);
		resp.setCharacterEncoding(encode);
		resp.setContentType("text/html;charset=UTF-8");

		// 过滤请求方法
		String method = req.getMethod();
		System.out.println("request method:" + method);
		if(methods.contains(method)){
			System.out.println("path:"+req.getRequestURL()+"need login");
		}
		
		// 过滤路径权限
		
		// 继续操作
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
