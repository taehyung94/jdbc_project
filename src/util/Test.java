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
			System.out.println("Oracle ����̹� �ε� ����");

			// Ŀ�ؼ� Ǯ ����
			cp = new ConnectionPool(initialCons, maxCons, block, timeout);
			System.out.println("ConnectionPool ����...");

			// Ŀ�ؼ� Ǯ�κ��� �ϳ��� Ŀ�ؼ� ������
			conn = cp.getConnection();

			// Connection�� ������ �ȵ�
			// conn.close();
			// Ŀ�ؼ� Ǯ���� ����� Ŀ�ؼ��� ������
			cp.releaseConnection(conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
