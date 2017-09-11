package net.codingme.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.ObjectUtils.Null;
import org.eclipse.jdt.internal.compiler.ast.FalseLiteral;

import net.codingme.po.User;
import net.codingme.util.ServletUtil;
import net.sf.json.JSONObject;
import sun.reflect.generics.tree.Tree;
import sun.util.resources.cldr.id.CalendarData_id_ID;


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

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//类型转换
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		// 设置编码
		req.setCharacterEncoding(encode);
		resp.setCharacterEncoding(encode);
		resp.setContentType("text/html;charset=UTF-8");

		//验证权限
		boolean checkAuthority = checkAuthority(req);
		if(checkAuthority){
			chain.doFilter(req, resp);
		}else{
			resp.getWriter().print("null");
		}
	}
	
	/**
	 * 验证权限
	 * @param request 请求的request
	 * @return	验证通过，返回true,失败false
	 */
	public boolean checkAuthority(HttpServletRequest request) {
		// 过滤请求方法,POST,PUT,DELETE要检查
		String method = request.getMethod();
		String url = request.getRequestURI().replace(request.getContextPath(),"");
		
		//需检查的路径
		String paths = "/api/user,/api/category,/api/news";
		if(!paths.contains(url)){
			return  true;
		}
		String methods="POST,PUT,DELETE";
		if(!methods.contains(method) && !url.startsWith("/api/user")){
			System.out.println("正常访问，无需登陆");
			return true;
		}
		
		//检查是否登陆
		Object object = request.getSession().getAttribute("user");
		if( object == null){
			System.out.println(" 敏感访问，你没有登陆");
			return false;
		}
		
		// 普通用户不能进行category的POST,PUT,DELETE操作
		if(url.startsWith("/api/category")){
			User user = (User)object;
			if(user.getType() != 1){
				return false;
			}
			System.out.println("敏感访问，超级管理员无视");
		}
		return true;
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public FilterGeneral() {
	}

	public void destroy() {
	}

}
