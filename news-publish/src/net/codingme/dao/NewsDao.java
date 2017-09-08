package net.codingme.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import net.codingme.po.News;

/** 
 * 信息的数据操作接口
 * 
 * @author Niu on 2017年9月6日 上午11:17:07
 */
public interface NewsDao {

	// 新增信息
	public boolean add(String sql, Object... args);

	// 删除信息
	public boolean delete(String sql, Object... args);

	// 更新信息
	public boolean update(String sql, Object... args);

	// 查询全部信息
	public List<News> select(String sql, Object... args);
	
	// 查询全部信息
	public List<News> likeSelect(String sql, Object... args);
	
	//解析数据
	public List<News> analyzeResultSet(ResultSet resultSet) throws SQLException;

}
