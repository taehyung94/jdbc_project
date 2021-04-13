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
		alert.setTitle("알림창");
		alert.setHeaderText("알림 메시지");
		
		String groupNameStr = groupName.getText();
		String groupCodeStr = groupCode.getText();
		if(groupNameStr.isEmpty()) {
			alert.setContentText("그룸명을 입력해 주세요.");
			alert.showAndWait();
			return;
		}
		if(groupCodeStr.isEmpty()) {
			// 그룹코드를 입력해 주세요. 팝업창을 보여준다.
			
			alert.setContentText("그룹 가입코드를 입력해 주세요.");
			alert.showAndWait();
			return;
		}
		
		if(idDuplicate == false) {
			alert.setContentText("가입코드 중복 확인을 해주세요.");
			alert.showAndWait();
			return;
		}
		
		
		boolean flag = groupDao.makeGroup(SessionData.getSessionData().get("id"), groupNameStr, groupCodeStr);
		if(!flag) {
			alert.setContentText("그룹 생성 실패!");
			alert.showAndWait();
			return;
		}
		else {
			alert.setContentText("그룹 생성 완료");
			alert.showAndWait();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/groupList.fxml"));
			AnchorPane pane = loader.load();
			
			//get the controller from the FXMLLoader
			GroupListController controller = loader.getController();
			
			//set it in the controller
			
			Scene scene = new Scene(pane);
			
			//creatAccountButton은 기존 Stage를 재활용하기 위한 접근수단
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
		
		//creatAccountButton은 기존 Stage를 재활용하기 위한 접근수단
		Stage primaryStage = (Stage) createGroupBtn.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void handleDuplicateCheck() {
		System.out.println("SignUpController:handleDuplicateCheck()");
		String groupCodeStr = groupCode.getText();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText("알림 메시지");
		System.out.println(groupCodeStr);
		if(groupCodeStr.isEmpty()) {
			// 그룹코드를 입력해 주세요. 팝업창을 보여준다.
			
			alert.setContentText("그룹 가입코드를 입력해 주세요.");
			alert.showAndWait();
			return;
		}
		
		boolean check = groupDao.groups_participate_id_check(groupCodeStr);
		System.out.println(check);
		if(check==false) {
			// 코드 중복(다른 코드 사용하도록 알림)을 사용자에게 알린다.
			alert.setContentText("존재하는 가입코드 입니다.");
			alert.showAndWait();
			idDuplicate = false;
			return;
		}
		alert.setContentText("사용 가능한 가입코드 입니다.");
		alert.showAndWait();
		idDuplicate = true;
		//groupCode.setEditable(false);
	}
}
