package com.xiyou.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.xiyou.jdbc.JDBCTools;

public class BaseDao {

	private static final QueryRunner runner = new QueryRunner();
	// Class<T> clazz 传入的是 泛型 T 对应的类型.class
	public <T> List<T> getForList(String sql , Class<T> clazz , Object ... args ){
		
		List<T> list = null;
		
		Connection conn = null;
		try {
			conn = JDBCTools.getInstance().getConnection();
			list = runner.query(conn, sql, new BeanListHandler<T>(clazz), args);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTools.getInstance().releaseDB(null, null, conn);
		}
		
		return list;
	}

	public <T> T get(String sql , Class<T> clazz , Object ... args){
		
		T result = null;
		Connection conn = null;
		
		try {
			conn = JDBCTools.getInstance().getConnection();
			result = runner.query(conn, sql,new  BeanHandler<T>(clazz), args);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTools.getInstance().releaseDB(null, null, conn);
		}
		return result;
	}
	
}
