package net.codingme.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.codingme.dao.CategoryDao;
import net.codingme.po.Category;
import net.codingme.util.JDBCTool;

/**
 * 导航栏目的数据操作
 * 
 * @author Niu on 2017年9月6日 上午11:08:39
 */
public class CategoryDaoImpl implements CategoryDao {

	/**
	 * 增加
	 */
	@Override
	public boolean add(String sql, Object... args) {
		return JDBCTool.generalUpdate(sql, args);
	}
	/**
	 * 删除
	 */
	@Override
	public boolean delete(String sql, Object... args) {
		return JDBCTool.generalUpdate(sql, args);
	}
	/**
	 * 更新
	 */
	@Override
	public boolean update(String sql, Object... args) {
		return JDBCTool.generalUpdate(sql, args);
	}

	/**
	 * 通用查询
	 * 
	 * @param sql SQL语句，自定义的值用问号表示
	 * @param args  参数列表，对应sql中问号顺序
	 * @return List
	 */
	@Override
	public List<Category> select(String sql, Object... args) {
		Connection conn = JDBCTool.getConnection();
		ResultSet resultSet = null;
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			int index = 1;
			if (args != null) {
				for (Object object : args) {
					pst.setObject(index++, object);
				}
			}
			resultSet = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<Category> list =null;
		try {
			list= analyzeResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCTool.close(resultSet, null, conn);
		return list;
	}
	
	// 解析ResultSet
	public List<Category> analyzeResultSet(ResultSet resultSet) throws SQLException {
		List<Category> list = new ArrayList<Category>();
		while (resultSet.next()) {
			// 栏目id
			Integer cId = resultSet.getInt("C_ID");
			// 栏目名称
			String cName = resultSet.getString("C_NAME");
			Category category = new Category(cId, cName);
			list.add(category);
		}
		return list;
	}
	

	

}
