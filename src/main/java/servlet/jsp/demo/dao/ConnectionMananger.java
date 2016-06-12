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
    	Connection connection = getConnection();
        if(connection != null)
			try {
				connection.close();
				/**
				 * 注意：关闭数据库连接后必须把ThreadLocal里的Connection给remove掉，要不然会报错：
				 * com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: No operations allowed after connection closed.
				 * 报错原因可能是因为对于每次访问servlet并不是都用一个新的线程，而是从线程池中获取一个线程
				 * 这样的话当第二次又拿到上一次用过的同一个线程的时候由于上一次用到的Connection没有从这个线程的ThreadLocal中remove掉，
				 * 所以并不会新建立一个连接，用的还是上一次已经关闭的数据库连接，这样就报错了。
				 */
				connectionHolder.remove(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
    }
}
