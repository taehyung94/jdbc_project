package test;

import java.util.List;

import dao.BoardCategoryDAO;
import dao.BoardDAO;
import dao.GroupEnrollDAO;
import dao.GroupsDAO;
import dao.MemberDAO;
import vo.BoardCategoryVO;
import vo.BoardPagingVO;
import vo.BoardVO;

public class DAOTest {
	public static void main(String[] args) {
		BoardDAO dao = new BoardDAO();
		List<BoardPagingVO> list =dao.read_board_list_with_searching_and_paging(101, 41, 1, "title", "testt");
		list.stream().forEach(b->System.out.println(b));
	}
}
