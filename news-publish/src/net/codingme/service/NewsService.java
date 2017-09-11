package net.codingme.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.codingme.po.News;

/**
 * 信息的业务逻辑接口
 * 
 * @author Niu on 2017年9月5日 下午3:06:27
 */
public interface NewsService {

	// 新增信息
	public String add(HttpServletRequest request);

	// 删除信息
	public boolean delete(HttpServletRequest request);

	// 更新信息
	public String update(HttpServletRequest request);

	// 查询全部信息
	public List<News> select(HttpServletRequest request);
	
	public int selectCount(String cId);

}
