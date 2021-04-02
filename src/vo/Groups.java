package vo;

public class Groups {
	Integer id;
	Integer host_member_id;
	String name;
	String participate_id;
	public Groups(Integer id, Integer host_member_id, String name, String participate_id) {
		super();
		this.id = id;
		this.host_member_id = host_member_id;
		this.name = name;
		this.participate_id = participate_id;
	}
	public Groups() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getHost_member_id() {
		return host_member_id;
	}
	public void setHost_member_id(Integer host_member_id) {
		this.host_member_id = host_member_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParticipate_id() {
		return participate_id;
	}
	public void setParticipate_id(String participate_id) {
		this.participate_id = participate_id;
	}
	
}
