package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import util.DBConnection;

public class GroupEnrollDAO {
	private Connection conn;
	private CallableStatement stmt;
	private ResultSet st;
	boolean signup_group(Integer member_id, Integer groups_id) {
		boolean flag = true;
		try {
			conn=DBConnection.getConnection();
			String query = "{ call groups_enroll_package.signup_group(?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, member_id);
			stmt.setInt(2, groups_id);
			stmt.executeUpdate(); 
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return flag;
	}
    public boolean signout_group(Integer member_id, Integer groups_id) {
    	boolean flag = true;
		try {
			conn=DBConnection.getConnection();
			String query = "{  call groups_enroll_package.signout_group(?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, member_id);
			stmt.setInt(2, groups_id);
			stmt.executeUpdate(); 
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return flag;
    }
}
