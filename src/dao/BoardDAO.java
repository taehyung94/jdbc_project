package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oracle.jdbc.OracleTypes;
import util.DBConnection;
import vo.BoardPagingVO;
import vo.BoardVO;

public class BoardDAO {
	private Connection conn;
	private CallableStatement stmt;
	private ResultSet st;
	public void write_board(Integer member_id, Integer groups_id, Integer board_category_id, String board_title, String board_content) {
		try {
			conn=DBConnection.getConnection();
			String query = "{ call board_package.write_board(?, ?, ?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, member_id);
			stmt.setInt(2, groups_id);
			stmt.setInt(3, board_category_id);
			stmt.setString(4, board_title);
			stmt.setString(5, board_content);
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
	}
	public boolean board_owner_check(Integer board_id, Integer member_id, Integer groups_id) {
		boolean flag = true;
		try {
			conn=DBConnection.getConnection();
			String query = "{ ? = call board_package.board_owner_check(?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, Types.INTEGER);
			stmt.setInt(2, board_id);
			stmt.setInt(3, member_id);
			stmt.setInt(4, groups_id);
			int x = stmt.executeUpdate();
			flag = (x == 1 ) ? true : false; 
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}		
		return flag;
	}
	public boolean edit_board(Integer board_id, Integer member_id, Integer groups_id, String board_title, String board_content) {
		boolean flag = true;
		try {
			conn=DBConnection.getConnection();
			String query = "{ call board_package.edit_board(?, ?, ?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, board_id);
			stmt.setInt(2, member_id);
			stmt.setInt(3, groups_id);
			stmt.setString(4, board_title);
			stmt.setString(5, board_content);
			int x= stmt.executeUpdate();
			System.out.println(x);
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return flag;
	}
	
	public boolean delete_board(Integer member_id, Integer board_id, Integer groups_id) {
		boolean flag = true;
		try {
			conn=DBConnection.getConnection();
			String query = "{ call board_package.delete_board(?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, member_id);
			stmt.setInt(2, board_id);
			stmt.setInt(3, groups_id);
			stmt.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return flag;
	}
	
	public BoardVO read_board_detail(Integer board_id) {
		BoardVO boardvo = new BoardVO();
		try {
			conn=DBConnection.getConnection();
			String query = "{ call board_package.read_board_detail(?, ?, ?, ?, ?, ?, ?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.setInt(1, board_id);
			stmt.registerOutParameter(2, Types.INTEGER);
			stmt.registerOutParameter(3, Types.INTEGER);
			stmt.registerOutParameter(4, Types.VARCHAR);
			stmt.registerOutParameter(5, Types.INTEGER);
			stmt.registerOutParameter(6, Types.INTEGER);
			stmt.registerOutParameter(7, Types.VARCHAR);
			stmt.registerOutParameter(8, Types.VARCHAR);
			stmt.registerOutParameter(9, Types.DATE);
			stmt.execute();
			boardvo.setId(stmt.getInt(2));
			boardvo.setWriter_id(stmt.getString(3));
			boardvo.setWriter_name(stmt.getString(4));
			boardvo.setGroups_id(stmt.getInt(5));
			boardvo.setBoard_category_id(stmt.getInt(6));
			boardvo.setTitle(stmt.getString(7));
			boardvo.setContent(stmt.getString(8));
			boardvo.setWrite_date(stmt.getDate(9));
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return boardvo;
	}
	
	public List<BoardPagingVO> read_board_list_with_paging(Integer groups_id, Integer board_category_id, Integer page_number){
		List<BoardPagingVO> board_list = new ArrayList<BoardPagingVO>();
		try {
			conn=DBConnection.getConnection();
			String query = "{ ? = call board_package.read_board_list_with_paging(?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.setInt(2, groups_id);
			stmt.setInt(3, board_category_id);
			stmt.setInt(4, page_number);
			stmt.execute();
			st = (ResultSet)stmt.getObject(1);
			while(st.next()) {
				BoardPagingVO vo = new BoardPagingVO();
				vo.setBnum(st.getInt("bnum"));
				vo.setId(st.getInt("id"));
				vo.setTitle(st.getString("title"));
				vo.setWriter_name(st.getString("writer_name"));
				vo.setWrite_date(st.getDate("write_date"));
				board_list.add(vo);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return board_list;
	}
	public List<BoardPagingVO> read_board_list_with_searching_and_paging(Integer groups_id, Integer board_category_id, Integer page_number, String kind, String keyword){
		List<BoardPagingVO> board_list = new ArrayList<BoardPagingVO>();
		try {
			conn=DBConnection.getConnection();
			String query = "{ ? = call board_package.read_board_list_with_searching_and_paging(?, ?, ?, ?, ?) }";
			stmt = conn.prepareCall(query);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.setInt(2, groups_id);
			stmt.setInt(3, board_category_id);
			stmt.setInt(4, page_number);
			stmt.setString(5, kind);
			stmt.setString(6, keyword);
			stmt.execute();
			st = (ResultSet)stmt.getObject(1);
			while(st.next()) {
				BoardPagingVO vo = new BoardPagingVO();
				vo.setBnum(st.getInt("bnum"));
				vo.setId(st.getInt("id"));
				vo.setTitle(st.getString("title"));
				vo.setWriter_name(st.getString("writer_name"));
				vo.setWrite_date(st.getDate("write_date"));
				board_list.add(vo);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.connectionClose(conn, stmt, st);
		}
		return board_list;
	}
}