package net.codingme.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.codingme.po.Category;

/** 
 * 导航栏目的数据操作接口
 * 
 * @author Niu on 2017年9月6日 下午12:37:08
 */
public interface CategoryDao {

	// 新增栏目
	public boolean add(String sql, Object... args);

	// 删除栏目
	public boolean delete(String sql, Object... args);

	// 更新栏目
	public boolean update(String sql, Object... args);

	// 查询全部栏目
	public List<Category> select(String sql, Object... args);
	
	//解析数据
	public List<Category> analyzeResultSet(ResultSet resultSet) throws SQLException;


}
