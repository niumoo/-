package net.codingme.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * 常用操作
 * 
 * @author Niu on 2017年9月6日 下午1:10:09
 */
public class ServletUtil {
	/**
	 * 返回到页面	如果以/开头，则跳转到根目录
	 * 			如果非/开头，则跳转到/WEB-INF/view/下
	 * @param jsp
	 * @param request
	 * @param response
	 */
	public static void returnJSP(String jsp, HttpServletRequest request, HttpServletResponse response) {
		
		if (!jsp.startsWith("/")) {
			jsp = "/WEB-INF/view/" + jsp;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 把参数列表转换成map	id=1&name=x
	 * @param request
	 * @return
	 */
	public static Map<String, String> urlToMap(HttpServletRequest request){
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(request.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String paramStr=null;
		Map<String, String> map = new HashMap<>();
		try {
			paramStr=in.readLine();
			String[] arrayUrl = paramStr.split("&");
			for (String url : arrayUrl) {
				String key = url.split("=")[0];
				String value = url.split("=")[1];
				map.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
