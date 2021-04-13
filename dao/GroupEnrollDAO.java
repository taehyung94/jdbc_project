package application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import application.util.DBConnection;
import application.vo.GroupEnrollDataVO;
import oracle.jdbc.OracleTypes;

public class GroupEnrollDAO {
	private Connection conn;
	private CallableStatement stmt;
	private ResultSet st;
	public boolean signup_group(Integer member_id, Integer groups_id) {
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
    
    public List<GroupEnrollDataVO> group_enroll_list_within_week(Integer groups_id){
    	List<GroupEnrollDataVO> list = new ArrayList<GroupEnrollDataVO>();
    	try {
			conn=DBConnection.getConnection();
			String query = "{ ? = call groups_enroll_package.group_enroll_list_within_week(?) }";
			stmt = conn.prepareCall(query);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.setInt(2, groups_id);
			st = (ResultSet)stmt.executeQuery();
			while(st.next()) {
				list.add(new GroupEnrollDataVO(st.getDate(1), st.getInt(2)));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
    	return list;
    }
}
