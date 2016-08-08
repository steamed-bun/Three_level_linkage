package com.xiyou.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC �Ĺ�����
 * 
 * ���а���: ��ȡ���ݿ�����, �ر����ݿ���Դ�ȷ���.
 */
public class JDBCTools {

	private static DataSource dataSource = null;

	//���ݿ����ӳ�Ӧֻ����ʼ��һ��. 
	static{
		dataSource = new ComboPooledDataSource("c3p0");
	}
	
	public Connection getConnection(){
		Connection conn = null;
		if(dataSource != null){
			try {
				conn = dataSource.getConnection();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return conn;
	}

	private JDBCTools(){}
	
	private static JDBCTools instance = new JDBCTools();
	
	public static JDBCTools getInstance(){
		return instance;
	}
	
	
	public void releaseDB(ResultSet resultSet, Statement statement,
			Connection connection) {

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				//���ݿ����ӳص� Connection ������� close ʱ
				//��������Ľ��йر�, ���ǰѸ����ݿ����ӻ�黹�����ݿ����ӳ���. 
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
