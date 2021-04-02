package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
	private static final int initialCons = 5;
	private static final int maxCons = 20;
	private static final boolean block = true;
	private static final long timeout = 10000;
	public static void main(String[] args) throws Exception {
		Connection conn;
		Statement stmt;
		ResultSet rs;
		ConnectionPool cp;
		try {
			System.out.println("Oracle 드라이버 로딩 성공");

			// 커넥션 풀 생성
			cp = new ConnectionPool(initialCons, maxCons, block, timeout);
			System.out.println("ConnectionPool 생성...");

			// 커넥션 풀로부터 하나의 커넥션 가져옴
			conn = cp.getConnection();

			// Connection을 닫으면 안됨
			// conn.close();
			// 커넥션 풀에게 사용한 커넥션을 돌려줌
			cp.releaseConnection(conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
