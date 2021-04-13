package application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import application.util.DBConnection;
import application.vo.BoardCategoryVO;
import oracle.jdbc.OracleTypes;

public class BoardCategoryDAO {
	private Connection conn;
	private CallableStatement stmt;
	private ResultSet st;
	public boolean make_board_category(Integer groups_id, String board_category_name) {
		boolean flag = true;
		try {
			conn=DBConnection.getConnection();
			String query = "{ call board_category_package.make_board_category(?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, groups_id);
			stmt.setString(2, board_category_name);
			stmt.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return flag;
	}
	public List<BoardCategoryVO> get_board_category_list(Integer groups_id){
		List<BoardCategoryVO> category_list = new ArrayList<BoardCategoryVO>();
		try {
			conn=DBConnection.getConnection();
			String query = "{ call board_category_package.get_board_category_list(?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, groups_id);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.execute();
			st = (ResultSet)stmt.getObject(2);
			while(st.next()) {
				Integer id = st.getInt("id");
				Integer groups_ids = st.getInt("groups_id");
				String name  = st.getString("name");
				Integer board_count = st.getInt("board_count");
				category_list.add(new BoardCategoryVO(id, groups_ids, name, board_count));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}		
		return category_list;
	}
	
	public Integer get_board_cnt_with_category(Integer groups_id, Integer board_category_id) { //카테고리의 게시글 갯
		Integer cnt = 0;
		try {
			conn=DBConnection.getConnection();
			String query = "{ ? = call board_package.get_board_cnt_with_category(?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, groups_id);
			stmt.setInt(3, board_category_id);
			stmt.execute();
			cnt = stmt.getInt(1);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}	
		return cnt;
		
	}
	public Integer get_board_category_count(Integer groups_id, Integer board_category_id) { //그룹의 카테고리 갯
		Integer cnt = 0;
		try {
			conn=DBConnection.getConnection();
			String query = "{ ? = call board_category_package.get_board_category_count(?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, groups_id);
			stmt.setInt(3, board_category_id);
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
	