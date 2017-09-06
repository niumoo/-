package net.codingme.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.codingme.dao.AdminDao;
import net.codingme.po.Admin;
import net.codingme.util.JDBCTool;

/** 
 * 用户的数据操作
 * 
 * @author Niu on 2017年9月6日 下午12:43:07
 */
public class AdminDaoImpl implements AdminDao {

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
	public List<Admin> select(String sql, Object... args) {
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
		
		List<Admin> list =null;
		try {
			list= analyzeResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCTool.close(resultSet, null, conn);
		return list;
	}
	
	// 解析ResultSet
	public List<Admin> analyzeResultSet(ResultSet resultSet) throws SQLException {
		List<Admin> list = new ArrayList<Admin>();
		while (resultSet.next()) {
			//用户ID
			Integer userId = resultSet.getInt("USER_ID");
			//用户昵称
			String userName = resultSet.getString("USER_NAME");
			//用户登陆用的邮箱
			String userEmail = resultSet.getString("USER_NAME");
			//用户登陆用的密码
			String password = resultSet.getString("PASSWORD");
			//用户能管理的栏目ID
			String manegeCategory = resultSet.getString("MANAGE_CATEGORY");
			//用户的类型
			int type = resultSet.getInt("TYPE");
		    Admin admin = new Admin(userId, type, userName, userEmail, password, manegeCategory);
		    list.add(admin);
		}
		return list;
	}

	

}
