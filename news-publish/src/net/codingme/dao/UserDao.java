package net.codingme.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.codingme.po.User;

/** 
 * 管理员的数据操作接口
 * 
 * @author Niu on 2017年9月6日 下午12:42:09
 */
public interface UserDao {

	// 新增信息
	public boolean add(String sql, Object... args);

	// 删除信息
	public boolean delete(String sql, Object... args);

	// 更新信息
	public boolean update(String sql, Object... args);

	// 查询全部信息
	public List<User> select(String sql, Object... args);
	
	//解析数据
	public List<User> analyzeResultSet(ResultSet resultSet) throws SQLException;

}
