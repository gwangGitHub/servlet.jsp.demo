package servlet.jsp.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMananger {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/game";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123456";
	
//	@Deprecated 
//	public static Connection getConnection() {
		/**
		 * 如果把Connection编程类的成员变量，为了可以在closeConnection()方法中关闭Connection,那么就不是线程安全的：
		 * 试想场景线程1和2同时都拿到了同一个Connection，这时线程1用完Connection然后close了，这时候因为线程2也在用
		 * 同一个Connection被关闭了，就会报错
		**/
//		Connection con = null; // 创建用于连接数据库的Connection对象
//		try {
//			Class.forName(DRIVER);// 加载Mysql数据驱动
//			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);// 创建数据连接
//		} catch (Exception e) {
//			System.out.println("数据库连接失败" + e.getMessage());
//		}
//		return con; // 返回所建立的数据库连接
//	}
	
	//session也是同理
//	private static final ThreadLocal threadSession = new ThreadLocal();  
//	  
//	public static Session getSession() throws InfrastructureException {  
//	    Session s = (Session) threadSession.get();  
//	    try {  
//	        if (s == null) {  
//	            s = getSessionFactory().openSession();  
//	            threadSession.set(s);  
//	        }  
//	    } catch (HibernateException ex) {  
//	        throw new InfrastructureException(ex);  
//	    }  
//	    return s;  
//	} 
	
	//做到线程安全的获取数据库连接，从来可以只实例化一个dao调用方法
	private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {  
        @Override  
        protected Connection initialValue() {  
            Connection conn = null;  
            try {
				Class.forName(DRIVER);
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);// 创建数据连接 
            } catch (SQLException e) {  
                e.printStackTrace();  
            } catch (ClassNotFoundException e) {
            	e.printStackTrace();
            }
            return conn;  
        }  
    };  
  
    public static Connection getConnection() {  
        return connectionHolder.get();  
    }  
  
    public static void setConnection(Connection conn) {  
        connectionHolder.set(conn);  
    }
    
    public static void closeConnection() {
        if(getConnection() != null)
			try {
				getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    }
}
