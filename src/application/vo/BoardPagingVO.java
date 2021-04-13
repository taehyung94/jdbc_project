package application.vo;

import java.util.Date;

public class BoardPagingVO {
	private Integer bnum;
	private Integer id;
	private String title;
	private String writer_name;
	private Date write_date;
	private Integer view_cnt;
	
	public BoardPagingVO() {
		super();
	}
	public BoardPagingVO(Integer bnum, Integer id, String title, String writer_name, Date write_date, Integer view_cnt) {
		super();
		this.bnum = bnum;
		this.id = id;
		this.title = title;
		this.writer_name = writer_name;
		this.write_date = write_date;
		this.view_cnt = view_cnt;
	}
	public Integer getBnum() {
		return bnum;
	}
	public void setBnum(Integer bnum) {
		this.bnum = bnum;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter_name() {
		return writer_name;
	}
	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}
	public Date getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}
	
	public Integer getView_cnt() {
		return view_cnt;
	}
	public void setView_cnt(Integer view_cnt) {
		this.view_cnt = view_cnt;
	}
	@Override
	public String toString() {
		return "BoardPagingVO [bnum=" + bnum + ", id=" + id + ", title=" + title + ", writer_name=" + writer_name
				+ ", write_date=" + write_date + "]";
	}
	
}
