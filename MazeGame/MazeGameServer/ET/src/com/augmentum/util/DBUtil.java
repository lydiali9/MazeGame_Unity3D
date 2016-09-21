package com.augmentum.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

public class DBUtil {

	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://192.168.206.7:3306/mydb";
	private final static String USER = "root";
	private final static String PWD = "root";

	public static Connection getConn() {
		
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PWD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(ResultSet rs, Statement pstm, Connection conn) {

		try {
			
			if (rs != null) {
				rs.close();
			}
			
			if (pstm != null) {
				pstm.close();
			}
			
			if (conn != null) {
				conn.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
