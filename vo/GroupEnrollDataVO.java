package application.vo;

import java.util.Date;

public class GroupEnrollDataVO {
	private Date enroll_date;
	private Integer member_cnt;
	
	public GroupEnrollDataVO() {
		super();
	}
	public GroupEnrollDataVO(Date enroll_date, Integer member_cnt) {
		super();
		this.enroll_date = enroll_date;
		this.member_cnt = member_cnt;
	}
	public Date getEnroll_date() {
		return enroll_date;
	}
	public void setEnroll_date(Date enroll_date) {
		this.enroll_date = enroll_date;
	}
	public Integer getMember_cnt() {
		return member_cnt;
	}
	public void setMember_cnt(Integer member_cnt) {
		this.member_cnt = member_cnt;
	}
	
}
