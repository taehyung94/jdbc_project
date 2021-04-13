package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.dao.GroupEnrollDAO;
import application.dao.GroupsDAO;
import application.dao.MemberDAO;
import application.util.SessionData;
import application.vo.GroupPagingVO;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GroupListController implements Initializable{
	@FXML private Pane group1;
	@FXML private Pane group2;
	@FXML private Pane group3;
	@FXML private Pane group4;
	@FXML private Label groupName1;
	@FXML private Label groupName2;
	@FXML private Label groupName3;
	@FXML private Label groupName4;
	@FXML private Button groupButton1;
	@FXML private Button groupButton2;
	@FXML private Button groupButton3;
	@FXML private Button groupButton4;
	@FXML private TextField groupCode;
	@FXML private Label currentGroup;
	
	 
	@FXML private Pagination pagination;
	@FXML private Pane groupList;
	@FXML private Button logoutBtn;
	
	
	@FXML
	private Button btnList[] = new Button[4];
	@FXML
	private Label labelList[] = new Label[4];
	
	private final ChangeListener<Number> paginationChangeListener = (observable, oldValue, newValue) -> changePage(newValue);
	private MemberDAO memberDao = new MemberDAO();
	private GroupsDAO groupDao = new GroupsDAO();
	private GroupEnrollDAO enrollDao = new GroupEnrollDAO();
	private List<GroupPagingVO> list;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			list = memberDao.get_member_enroll_group_list_with_paging(SessionData.getSessionData().get("id"), 1);
			pagination.setPageCount((memberDao.get_member_group_cnt(SessionData.getSessionData().get("id"))/4)+1);
			pagination.setMaxPageIndicatorCount(3);
			pagination.currentPageIndexProperty().addListener(paginationChangeListener);
			currentGroup.setText("������ �׷� �� : "+memberDao.get_member_group_cnt(SessionData.getSessionData().get("id")));
			btnList[0] = groupButton1;
			btnList[1] = groupButton2;
			btnList[2] = groupButton3;
			btnList[3] = groupButton4;
			
			labelList[0] = groupName1;
			labelList[1] = groupName2;
			labelList[2] = groupName3;
			labelList[3] = groupName4;
			
			
			int size = list.size();
			System.out.println("������ : "+size);
			for(int i=0;i<4;i++) {
				if(i<size) {
					labelList[i].setText(list.get(i).getName());
					btnList[i].setDisable(false);
				}
				else {
					labelList[i].setText("���ο� �׷쿡 �����ϼ���.");
					btnList[i].setDisable(true);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * pagination.setPageFactory(new Callback<Integer, Node>() {
		 * 
		 * @Override public Node call(Integer param) { return new
		 * Label(String.format("Current Page: %d", pagination.getCurrentPageIndex())); }
		 * } );
		 */
	}
	
	
	public void selectGroup1() throws Exception {
		
		System.out.println("�׷�1���ӽõ�");
		SessionData.getSessionData().put("group_id", list.get(0).getId());

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/list.fxml"));
		AnchorPane pane = loader.load();
		//get the controller from the FXMLLoader
		ListController controller = loader.getController();
		
		//set it in the controller
		
		
		Scene scene = new Scene(pane);
		
		//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
		Stage primaryStage = (Stage) group1.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	public void selectGroup2() throws Exception {
		
		System.out.println("�׷�2���ӽõ�");
		SessionData.getSessionData().put("group_id", list.get(1).getId());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/list.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		ListController controller = loader.getController();
		
		//set it in the controller
		
		
		Scene scene = new Scene(pane);
		
		//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
		Stage primaryStage = (Stage) group1.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	public void selectGroup3() throws Exception {
	
	System.out.println("�׷�3���ӽõ�");
	SessionData.getSessionData().put("group_id", list.get(2).getId());
	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/list.fxml"));
	AnchorPane pane = loader.load();
	
	//get the controller from the FXMLLoader
	ListController controller = loader.getController();
	
	//set it in the controller
	
	
	Scene scene = new Scene(pane);
	
	//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
	Stage primaryStage = (Stage) group1.getScene().getWindow();
	
	primaryStage.setScene(scene);
	primaryStage.show();
	
}
	public void selectGroup4() throws Exception {
	
	System.out.println("�׷�4���ӽõ�");
	SessionData.getSessionData().put("group_id", list.get(3).getId());
	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/list.fxml"));
	AnchorPane pane = loader.load();
	
	//get the controller from the FXMLLoader
	ListController controller = loader.getController();
	
	//set it in the controller
	
	
	Scene scene = new Scene(pane);
	
	//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
	Stage primaryStage = (Stage) group1.getScene().getWindow();
	
	primaryStage.setScene(scene);
	primaryStage.show();
	
}

	
	private void changePage(Number newValue) { 
		//label.setText(String.format("Current Page: %d", pagination.getCurrentPageIndex()));
		int nextPage = (int)newValue+1;
		list = memberDao.get_member_enroll_group_list_with_paging(SessionData.getSessionData().get("id"), nextPage);
		int size = list.size();
		System.out.println("������ : "+size);
		for(int i=0;i<4;i++) {
			if(i<size) {
				labelList[i].setText(list.get(i).getName());
				btnList[i].setDisable(false);
			}
			else {
				labelList[i].setText("���ο� �׷쿡 �����ϼ���.");
				btnList[i].setDisable(true);
			}
		}
	}
	
	
	public void createGroup() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/createGroup.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		CreateGroupController controller = loader.getController();
		
		//set it in the controller
		
		Scene scene = new Scene(pane);
		
		//creatAccountButton�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
		Stage primaryStage = (Stage) group1.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void groupSignUp() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("�׷� ����");
		String groupCodeStr = groupCode.getText();
		
		if(groupCodeStr.isEmpty()) {
			alert.setContentText("�׷� �����ڵ带 �Է��ϼ���.");
			alert.showAndWait();
			return;
		}
		
		GroupPagingVO searchResult = groupDao.searchGroup(groupCodeStr);
		
		if(searchResult.getId() == null) {
			alert.setContentText("�������� �ʴ� �ڵ��Դϴ�.");
			alert.showAndWait();
			return;
		}
		
		alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(searchResult.getName()+"�׷쿡 �����Ͻðڽ��ϱ�?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			boolean flag = enrollDao.signup_group(SessionData.getSessionData().get("id"),searchResult.getId());
			alert.setAlertType(AlertType.INFORMATION);
			if(flag == false) {
				alert.setContentText("���Խ���! �̹� ������ �׷����� Ȯ���ϼ���.");
				alert.showAndWait();
				return;
			}
			
		    alert.setContentText(searchResult.getName()+"�׷쿡 ���ԿϷ�!");
		    alert.showAndWait();
		    currentGroup.setText("������ �׷� �� : "+memberDao.get_member_group_cnt(SessionData.getSessionData().get("id")));
		    changePage(0);
		    
		}
	}
	
	public void logout() throws Exception {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("�α׾ƿ� ��û");
		SessionData.getSessionData().clear();
		
		alert.setAlertType(AlertType.CONFIRMATION);;
		alert.setContentText("�α׾ƿ� �Ͻðڽ��ϱ�?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			
			alert.setAlertType(AlertType.INFORMATION);
			alert.setHeaderText("�α׾ƿ�");
			alert.setContentText("�α׾ƿ� �Ϸ�");
		    alert.showAndWait();
		    
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/login.fxml"));
			AnchorPane pane = loader.load();
			
			//get the controller from the FXMLLoader
			LoginController controller = loader.getController();
			
			//set it in the controller
			
			Scene scene = new Scene(pane);
			
			//creatAccountButton�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
			Stage primaryStage = (Stage) logoutBtn.getScene().getWindow();
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}
	}
	
    
}
