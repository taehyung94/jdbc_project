package application.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import application.dao.MemberDAO;
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

public class LoginController implements Initializable{
	private MemberDAO memberDao = new MemberDAO();
	
	@FXML
    private AnchorPane container;
    @FXML
    private Button loginButton;

    @FXML private TextField userId;
	@FXML private PasswordField userPwd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
	
	
		
	public void login() throws Exception  {
		System.out.println("ID :" + userId.getText());
		System.out.println("PASSWORD :" + userPwd.getText());
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText("알림 메시지");
		
		// 아이디 입력 검사
		String idStr = userId.getText();
		if(idStr.isEmpty()) {
			alert.setContentText("Email을 입력해 주세요.");
			alert.showAndWait();
			return;
		}
				
		// 비밀번호 입력 검사
		String pwStr = userPwd.getText();
		if(pwStr.isEmpty()) {
			alert.setContentText("비밀번호를 입력해 주세요.");
			alert.showAndWait();
			return;
		}
		
		
		boolean check = memberDao.email_check(idStr);
		
		if(check == true) { alert.setContentText("회원정보가 없습니다.");
		alert.showAndWait(); return; }
		 
		
		Map<String,String> login_data = memberDao.member_login_data_check(idStr);
		
		if(!login_data.get("password").equals(pwStr)) {
			alert.setContentText("비밀번호가 일치하지 않습니다.");
			alert.showAndWait();
			return;
		}
		//System.out.println("여기까지왔다");
		Map<String,Integer> sessionData = SessionData.getSessionData();
		Integer id = memberDao.get_member_id(idStr);
		sessionData.put("id", id);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/groupList.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		GroupListController controller = loader.getController();
		
		//set it in the controller
		 
        
		Scene scene = new Scene(pane);
		
		//registerBtn은 기존 Stage를 재활용하기 위한 접근수단
		Stage primaryStage = (Stage) loginButton.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
		/*
		 * if(txtUserName.getText().contentEquals("user") &&
		 * txtPassword.getText().equals("pass")) { lblStatus.setText("Login Success");
		 * Stage primaryStage = new Stage(); Parent root =
		 * FXMLLoader.load(getClass().getResource("/application/Main.fxml")); Scene
		 * scene = new Scene(root);
		 * scene.getStylesheets().add(getClass().getResource("application.css").
		 * toExternalForm()); primaryStage.setScene(scene); primaryStage.show(); }else {
		 * lblStatus.setText("Login Failed"); }
		 */
	}
	
	
	public void register() throws IOException {
		System.out.println("회원가입 시도");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/register.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		RegisterController controller = loader.getController();
		
		//set it in the controller
		
		Scene scene = new Scene(pane);
		
		//registerBtn은 기존 Stage를 재활용하기 위한 접근수단
		Stage primaryStage = (Stage) loginButton.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
}
