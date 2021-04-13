package application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import application.util.DBConnection;
import application.vo.GroupPagingVO;
import oracle.jdbc.OracleTypes;

public class GroupsDAO {
	private Connection conn;
	private CallableStatement stmt;
	private ResultSet st;
	public boolean groups_participate_id_check(String participate_id) {
		boolean flag = true;
		try {
			conn=DBConnection.getConnection();
			String query = "{ ? = call group_package.groups_participate_id_check(?) }";
			stmt = conn.prepareCall(query);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, participate_id);
			stmt.execute();
			Integer query_data = stmt.getInt(1); 
			//System.out.println(query_data);
			if(query_data == 0) flag = false;
			System.out.printf("%d %b %n", query_data, flag);
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return flag;
	}
	public boolean updateGroup(Integer group_id, Integer host_member_id, String name) {
		boolean flag = true;
		try {
			conn=DBConnection.getConnection();
			String query = "{ call group_package.update_group(?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, group_id);
			stmt.setInt(2, host_member_id);
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
	public boolean makeGroup(Integer member_id, String group_name, String participate_id) {
		boolean flag = true;
		try {
			conn=DBConnection.getConnection();
			String query = "{ call group_package.make_group(?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, member_id);
			stmt.setString(2, group_name);
			stmt.setString(3, participate_id);
			stmt.executeUpdate(); 
			//System.out.println(query_data);
			System.out.println(flag);
		} catch (Exception e){
			e.printStackTrace();
			flag=false;
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return flag;
	}
	public boolean deleteGroup(Integer group_id, Integer host_member_id) {
		boolean flag = true;
		try {
			conn=DBConnection.getConnection();
			String query = "{ call group_package.delete_group(?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, group_id);
			stmt.setInt(2, host_member_id);
			stmt.executeUpdate(); 
			System.out.println(flag);
		} catch (Exception e){
			e.printStackTrace();
			flag=false;
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return flag;
	}
	public GroupPagingVO searchGroup(String participate_id) {
		GroupPagingVO searchResult = new GroupPagingVO(); 
		try {
			conn=DBConnection.getConnection();
			String query = "{ call group_package.search_group(?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setString(1, participate_id);
			stmt.registerOutParameter(2, Types.INTEGER);
			stmt.registerOutParameter(3, Types.VARCHAR);
			stmt.execute();
			searchResult.setId(stmt.getInt(2));
			searchResult.setName(stmt.getString(3));
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return searchResult;
	}
}
