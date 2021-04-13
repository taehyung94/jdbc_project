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
		alert.setTitle("�˸�â");
		alert.setHeaderText("�˸� �޽���");
		
		// ���̵� �Է� �˻�
		String idStr = userId.getText();
		if(idStr.isEmpty()) {
			alert.setContentText("Email�� �Է��� �ּ���.");
			alert.showAndWait();
			return;
		}
				
		// ��й�ȣ �Է� �˻�
		String pwStr = userPwd.getText();
		if(pwStr.isEmpty()) {
			alert.setContentText("��й�ȣ�� �Է��� �ּ���.");
			alert.showAndWait();
			return;
		}
		
		
		boolean check = memberDao.email_check(idStr);
		
		if(check == true) { alert.setContentText("ȸ�������� �����ϴ�.");
		alert.showAndWait(); return; }
		 
		
		Map<String,String> login_data = memberDao.member_login_data_check(idStr);
		
		if(!login_data.get("password").equals(pwStr)) {
			alert.setContentText("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			alert.showAndWait();
			return;
		}
		//System.out.println("��������Դ�");
		Map<String,Integer> sessionData = SessionData.getSessionData();
		Integer id = memberDao.get_member_id(idStr);
		sessionData.put("id", id);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/groupList.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		GroupListController controller = loader.getController();
		
		//set it in the controller
		 
        
		Scene scene = new Scene(pane);
		
		//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
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
		System.out.println("ȸ������ �õ�");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/register.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		RegisterController controller = loader.getController();
		
		//set it in the controller
		
		Scene scene = new Scene(pane);
		
		//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
		Stage primaryStage = (Stage) loginButton.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
}
