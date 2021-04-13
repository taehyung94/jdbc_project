package application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLType;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.util.DBConnection;
import application.vo.GroupPagingVO;
import oracle.jdbc.OracleTypes;

public class MemberDAO {
	private Connection conn;
	private CallableStatement stmt;
	private ResultSet st;
	public Integer get_member_id(String email_id) {
		Integer id = 0;
		try {
			conn=DBConnection.getConnection();
			String query = "{ ? = call member_package.member_get_id(?) }";
			stmt = conn.prepareCall(query);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, email_id);
			stmt.execute();
			id = stmt.getInt(1); 
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return id;
	}
	public boolean email_check(String email_id) {
		boolean check= false;
		try {
			conn=DBConnection.getConnection();
			String query = "{ ? = call member_package.member_email_id_check(?) }";
			stmt = conn.prepareCall(query);
			stmt.registerOutParameter(1, Types.VARCHAR);
			stmt.setString(2, email_id);
			stmt.execute();
			String query_data = stmt.getString(1); 
			if(query_data.equals("ok")) {
				check=true;
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return check;
	}
	public boolean signup(String email_id, String password, String name) {
		boolean flag = true;
		try {
			conn=DBConnection.getConnection();
			String query = "{ call member_package.member_signup(?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setString(1, email_id);
			stmt.setString(2, password);
			stmt.setString(3, name);
			stmt.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return flag;
	}
	public void update_member(Integer id, String name, String password) {
		try {
			conn=DBConnection.getConnection();
			String query = "{ call member_package.member_information_change(?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, id);
			stmt.setString(2, name);
			stmt.setString(3, password);
			stmt.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
	}
	
	public Map<String, String> member_login_data_check(String email_id) {
		Map<String, String> login_data = new HashMap<String, String>();
		try {
			conn=DBConnection.getConnection();
			String query = "{ call member_package.member_get_login_data(?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setString(1, email_id);
			stmt.registerOutParameter(2, Types.VARCHAR);
			stmt.registerOutParameter(3, Types.VARCHAR);
			stmt.executeQuery();
			String v_email_id = stmt.getString(2);
			String v_password = stmt.getString(3);
			login_data.put("email_id", v_email_id);
			login_data.put("password", v_password);
			//System.out.printf("%s %s %n",v_email_id, v_password);
		} catch (Exception e){
			System.out.println("로그인 체크");
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return login_data;
	}
	public List<GroupPagingVO> get_member_enroll_group_list_with_paging(Integer id, int pagenum) {
		List<GroupPagingVO> groupsList = new ArrayList<GroupPagingVO>();
		try {
			conn=DBConnection.getConnection();
			String query = "{ ? = call member_package.member_get_group_list_with_paging(?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.setInt(2, id);
			stmt.setInt(3, pagenum);
			stmt.execute();
			st = (ResultSet) stmt.getObject(1);
			while(st.next()) {
				groupsList.add(new GroupPagingVO(st.getInt("id"),st.getString("name")));
				//System.out.printf("%d %s %n",st.getInt("id"), st.getString("name"));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return groupsList;
	}
	public Integer get_member_group_cnt(Integer id) {
		Integer cnt = 0;
		try {
			conn=DBConnection.getConnection();
			String query = "{ ? = call member_package.get_member_enroll_group_cnt(?) }";
			stmt = conn.prepareCall(query);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, id);
			stmt.execute();
			cnt = stmt.getInt(1);
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return cnt;
	}
}
