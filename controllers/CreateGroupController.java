package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.dao.GroupsDAO;
import application.util.SessionData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreateGroupController implements Initializable{
	
	@FXML private AnchorPane container;
	@FXML private TextField groupName;
	@FXML private PasswordField groupCode;
	@FXML private Button createGroupBtn;
	
	private GroupsDAO groupDao = new GroupsDAO();
	private boolean idDuplicate = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	
	public void createGroup() throws Exception {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("�˸� �޽���");
		
		String groupNameStr = groupName.getText();
		String groupCodeStr = groupCode.getText();
		if(groupNameStr.isEmpty()) {
			alert.setContentText("�׷���� �Է��� �ּ���.");
			alert.showAndWait();
			return;
		}
		if(groupCodeStr.isEmpty()) {
			// �׷��ڵ带 �Է��� �ּ���. �˾�â�� �����ش�.
			
			alert.setContentText("�׷� �����ڵ带 �Է��� �ּ���.");
			alert.showAndWait();
			return;
		}
		
		if(idDuplicate == false) {
			alert.setContentText("�����ڵ� �ߺ� Ȯ���� ���ּ���.");
			alert.showAndWait();
			return;
		}
		
		
		boolean flag = groupDao.makeGroup(SessionData.getSessionData().get("id"), groupNameStr, groupCodeStr);
		if(!flag) {
			alert.setContentText("�׷� ���� ����!");
			alert.showAndWait();
			return;
		}
		else {
			alert.setContentText("�׷� ���� �Ϸ�");
			alert.showAndWait();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/groupList.fxml"));
			AnchorPane pane = loader.load();
			
			//get the controller from the FXMLLoader
			GroupListController controller = loader.getController();
			
			//set it in the controller
			
			Scene scene = new Scene(pane);
			
			//creatAccountButton�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
			Stage primaryStage = (Stage) createGroupBtn.getScene().getWindow();
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
	}
	
	public void cancel() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/groupList.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		GroupListController controller = loader.getController();
		
		//set it in the controller
		
		Scene scene = new Scene(pane);
		
		//creatAccountButton�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
		Stage primaryStage = (Stage) createGroupBtn.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void handleDuplicateCheck() {
		System.out.println("SignUpController:handleDuplicateCheck()");
		String groupCodeStr = groupCode.getText();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("�˸� �޽���");
		System.out.println(groupCodeStr);
		if(groupCodeStr.isEmpty()) {
			// �׷��ڵ带 �Է��� �ּ���. �˾�â�� �����ش�.
			
			alert.setContentText("�׷� �����ڵ带 �Է��� �ּ���.");
			alert.showAndWait();
			return;
		}
		
		boolean check = groupDao.groups_participate_id_check(groupCodeStr);
		System.out.println(check);
		if(check==false) {
			// �ڵ� �ߺ�(�ٸ� �ڵ� ����ϵ��� �˸�)�� ����ڿ��� �˸���.
			alert.setContentText("�����ϴ� �����ڵ� �Դϴ�.");
			alert.showAndWait();
			idDuplicate = false;
			return;
		}
		alert.setContentText("��� ������ �����ڵ� �Դϴ�.");
		alert.showAndWait();
		idDuplicate = true;
		//groupCode.setEditable(false);
	}
}
