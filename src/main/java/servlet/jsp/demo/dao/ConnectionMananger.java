package servlet.jsp.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionMananger {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/game";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123456";

	public static Connection getConnection() {
		Connection con = null; // 创建用于连接数据库的Connection对象
		try {
			Class.forName(DRIVER);// 加载Mysql数据驱动
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);// 创建数据连接
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return con; // 返回所建立的数据库连接
	}
}
