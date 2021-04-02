package vo;

import java.util.Date;

public class BoardVO {
	Integer id;
	String writer_id;
	String writer_name;
	Integer groups_id;
	Integer board_category_id;
	String title;
	String content;
	Date write_date;
	public BoardVO() {
		super();
	}
	public BoardVO(Integer id, String writer_id, String writer_name, Integer groups_id, Integer board_category_id,
			String title, String content, Date write_date) {
		super();
		this.id = id;
		this.writer_id = writer_id;
		this.writer_name = writer_name;
		this.groups_id = groups_id;
		this.board_category_id = board_category_id;
		this.title = title;
		this.content = content;
		this.write_date = write_date;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public String getWriter_name() {
		return writer_name;
	}
	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}
	public Integer getGroups_id() {
		return groups_id;
	}
	public void setGroups_id(Integer groups_id) {
		this.groups_id = groups_id;
	}
	public Integer getBoard_category_id() {
		return board_category_id;
	}
	public void setBoard_category_id(Integer board_category_id) {
		this.board_category_id = board_category_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}
	@Override
	public String toString() {
		return "BoardVO [id=" + id + ", writer_id=" + writer_id + ", writer_name=" + writer_name + ", groups_id="
				+ groups_id + ", board_category_id=" + board_category_id + ", title=" + title + ", content=" + content
				+ ", write_date=" + write_date + "]";
	}
	
}
