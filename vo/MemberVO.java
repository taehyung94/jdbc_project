package application.vo;

public class MemberVO {
	private Integer id;
	private String email_id;
	private String password;
	private String name;
	private Integer group_cnt;
	public MemberVO() {
		super();
	}
	public MemberVO(Integer id, String email_id, String password, String name, Integer group_cnt) {
		super();
		this.id = id;
		this.email_id = email_id;
		this.password = password;
		this.name = name;
		this.group_cnt = group_cnt;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGroup_cnt() {
		return group_cnt;
	}
	public void setGroup_cnt(Integer group_cnt) {
		this.group_cnt = group_cnt;
	}
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", email_id=" + email_id + ", password=" + password + ", name=" + name
				+ ", group_cnt=" + group_cnt + "]";
	}
	
}
