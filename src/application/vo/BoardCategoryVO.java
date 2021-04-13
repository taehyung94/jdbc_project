package application.vo;

public class BoardCategoryVO {
	private Integer id;
	private Integer groups_id;
	private String name;
	private Integer board_count;
	public BoardCategoryVO() {
		super();
	}
	public BoardCategoryVO(Integer id, Integer groups_id, String name, Integer board_count) {
		super();
		this.id = id;
		this.groups_id = groups_id;
		this.name = name;
		this.board_count = board_count;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGroups_id() {
		return groups_id;
	}
	public void setGroups_id(Integer groups_id) {
		this.groups_id = groups_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBoard_count() {
		return board_count;
	}
	public void setBoard_count(Integer board_count) {
		this.board_count = board_count;
	}
	@Override
	public String toString() {
		return "BoardCategoryVO [id=" + id + ", groups_id=" + groups_id + ", name=" + name + ", board_count="
				+ board_count + "]";
	}
	
	
}
