package application.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private static Connection conn;

	private DBConnection() {
	}
	public static Connection getConnection() throws Exception{
		if(conn == null || conn.isClosed()) {
			Properties properties  = new Properties();
			Reader reader;
			try {
				reader = new FileReader("lib/oracle.properties");  // 읽어올 파일 지정
				properties.load(reader);                           // 설정 파일 로딩하기
			} catch (FileNotFoundException e1) {
				System.out.println("예외: 지정한 파일을 찾을수없습니다 :" + e1.getMessage());
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String driverName = properties.getProperty("driver");
			String url = properties.getProperty("url");
			String user = properties.getProperty("user");
			String pwd = properties.getProperty("password");

			try {
				Class.forName(driverName);
				conn = DriverManager.getConnection(url, user, pwd);
				System.out.println("connection success");
			}

			catch (ClassNotFoundException e) {
				System.out.println("예외: 드라이버로드 실패 :" + e.getMessage());
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("예외: connection fail :" + e.getMessage());
				e.printStackTrace();
			}
		}
		return conn;
	}
	public static boolean connectionClose(Connection con, CallableStatement stmt, ResultSet st) {
		boolean flag = true;
		try {
			if(con!=null) con.close();
			if(stmt!=null) stmt.close();
			if(st!= null) st.close();
		} catch (Exception e) {
			// TODO: handle exception
			flag=false;
		} finally {
			
		}
		return flag;
	}
}