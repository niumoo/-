package net.codingme.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 封装JDBC后的通用操作类
 * 
 * @author Niu on 2017年9月7日 上午10:40:04
 */
public class JDBCTool {
	
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/news-publish?useUnicode=true&amp;characterEncoding=utf-8";
	private static String USERNAME = "root";
	private static String PWD = "123";
	private static Connection conn = null;

	static {
		try {
			// 注册驱动
			Class.forName(DRIVER);
			// 获取数据库连接
			conn = DriverManager.getConnection(URL, USERNAME, PWD);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 静态方法得到数据库连接
	public static Connection getConnection() {
		return conn;
	}

	// 静态方法关闭资源
	public static void close(ResultSet resultSet,Statement statement, Connection connection) {
		try {
			if(resultSet != null){
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通用增加，删除，更新数据方法
	 * 
	 * @param sql SQL语句，自定义的值用问号表示
	 * @param args  参数列表，对应sql中问号顺序
	 * @return	boolean
	 */
	public static boolean generalUpdate(String sql, Object... args) {
		Connection conn = JDBCTool.getConnection();
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			int index = 1;
			for (Object object : args) {
				pst.setObject(index++, object);
			}
			int result = pst.executeUpdate();
			if (result == 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, null, conn);
		}
		return true;
	}
	
	
	
	
}
