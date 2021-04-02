package vo;

public class GroupPagingVO {
	private Integer id;
	private String name;
	
	public GroupPagingVO() {
		super();
	}

	public GroupPagingVO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
