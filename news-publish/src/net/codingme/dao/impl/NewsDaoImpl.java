package net.codingme.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import net.codingme.dao.NewsDao;
import net.codingme.po.News;
import net.codingme.util.JDBCTool;

/**
 * 信息的数据操作
 * 
 * @author Niu on 2017年9月5日 下午11:15:15
 */
public class NewsDaoImpl implements NewsDao{

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
	public List<News> select(String sql, Object... args) {
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
		
		List<News> list =null;
		try {
			list= analyzeResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCTool.close(resultSet, null, conn);
		return list;
	}

	/**
	 * 模糊查询
	 * 
	 * @param sql SQL语句，自定义的值用问号表示
	 * @param args  参数列表，对应sql中问号顺序
	 * @return List
	 */
	@Override
	public List<News> likeSelect(String sql, Object... args) {
		Connection conn = JDBCTool.getConnection();
		ResultSet resultSet = null;
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			int index = 1;
			if (args != null) {
				for (Object object : args) {
					pst.setObject(index++, "%"+object+"%");
				}
			}
			resultSet = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<News> list = null;
		try {
			while (resultSet.next()) {
				//信息id
				Integer newsId = resultSet.getInt("NEWS_ID");
				//信息标题
				String newsTitle = resultSet.getString("NEWS_TITLE");
			    News news = new News(newsId, null, newsTitle, null, null, null, null,null);
			    if(list == null){
			    	list = new ArrayList<News>();
			    }
			    list.add(news);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCTool.close(resultSet, null, conn);
		return list;
	}
	
	// 解析ResultSet
	public List<News> analyzeResultSet(ResultSet resultSet) throws SQLException {
		List<News> list = new ArrayList<News>();
		while (resultSet.next()) {
			//信息id
			Integer newsId = resultSet.getInt("NEWS_ID");
			//信息所属栏目id
			Integer cId = resultSet.getInt("C_ID");
			//信息标题
			String newsTitle = resultSet.getString("NEWS_TITLE");
			//信息正文
		    String newsContent = resultSet.getString("NEWS_CONTENT");
			//附件1
		    String newsFile1 = resultSet.getString("NEWS_FILE1");
			//附件2
		    String newsFile2 = resultSet.getString("NEWS_FILE2");
			//附件3
		    String newsFile3 = resultSet.getString("NEWS_FILE3");
		    //发布时间
		    Date newsCreateTime = resultSet.getDate("NEWS_CREATE_TIME");
		    SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String createTime = dateFormater.format(newsCreateTime);
		    News news = new News(newsId, cId, newsTitle, newsContent, newsFile1, newsFile2, newsFile3,createTime);
		    list.add(news);
		}
		return list;
	}
	

}
