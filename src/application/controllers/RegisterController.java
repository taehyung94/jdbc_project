package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.dao.MemberDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController implements Initializable {
	private MemberDAO memberDao = new MemberDAO();
	
	@FXML
	private AnchorPane container;
	@FXML
	private Button createAccountButton;
	@FXML
	private TextField name;
	@FXML
	private TextField email;
	@FXML
	private PasswordField pwd;
	@FXML
	private PasswordField cpwd;
	@FXML
	private CheckBox agree;
	@FXML
	private Button check;
	
	private boolean idDuplicate = false;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}
	
	@FXML
	public void handleDuplicateCheck() throws IOException {
		System.out.println("SignUpController:handleDuplicateCheck()");
		String idStr = email.getText();
		String e_pattern =  "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
		System.out.println(idStr);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("�˸� �޽���");
		if(!idStr.matches(e_pattern)) {
			alert.setContentText("Email ���Ŀ� ���� �ʽ��ϴ�.");
			alert.showAndWait();
			return;
		}
		if(idStr.equals("")) {
			// ���̵� �Է��� �ּ���. �˾�â�� �����ش�.
			
			alert.setContentText("Email�� �Է��� �ּ���.");
			alert.showAndWait();
			//.showPopup(stage, "���̵� �Է��� �ּ���.");
			return;
		}
		
		boolean check = memberDao.email_check(idStr);
		
		if(check==false) {//commonData.exists(idStr)) {
			// ���̵� �ߺ�(�ٸ� ���̵� ����ϵ��� �˸�)�� ����ڿ��� �˸���.
			alert.setContentText("�̹� ������ Email �Դϴ�.");
			alert.showAndWait();
			idDuplicate = false;
			return;
		}
		alert.setContentText("��� ������ Email �Դϴ�.");
		alert.showAndWait();
		//dialogCommon.showPopup(stage, "��� ������ ���̵� �Դϴ�.");
		idDuplicate = true;
	}

	@FXML
	public void handleSignup() throws IOException {
		System.out.println("SignUpController:handleSignup()");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("�˸� �޽���");
		// �̸� �Է� �˻�
		String nameStr = name.getText();
		if(nameStr.isEmpty()) {
			alert.setContentText("�̸��� �Է��� �ּ���.");
			alert.showAndWait();
			return;
		}
		
		// ���̵� �Է� �˻�
		String idStr = email.getText();
		if(idStr.isEmpty()) {
			alert.setContentText("Email�� �Է��� �ּ���.");
			alert.showAndWait();
			return;
		}
		
		// ��й�ȣ �Է� �˻�
		String pwStr = pwd.getText();
		if(pwStr.isEmpty()) {
			alert.setContentText("��й�ȣ�� �Է��� �ּ���.");
			alert.showAndWait();
			return;
		}
		
		// ��й�ȣ Ȯ�� �Է� �˻�
		String pwConfirmStr = cpwd.getText();
		if(pwConfirmStr.isEmpty()) {
			alert.setContentText("��й�ȣ Ȯ���� �Է��� �ּ���.");
			alert.showAndWait();
			return;
		}
		
		// ��й�ȣ�� ��й�ȣ Ȯ���� ��ġ�ϴ��� �˻�
		if(!pwStr.equals(pwConfirmStr)) {
			alert.setContentText("��й�ȣ�� ��ġ�ؾ� �մϴ�.");
			alert.showAndWait();
			cpwd.setText("");
			return;
		}
		
		if(idDuplicate == false) {
			alert.setContentText("Email �ߺ� Ȯ���� ���ּ���.");
			alert.showAndWait();
			return;
		}
		
		
		System.out.println(agree);
		if(!agree.isSelected()) {
			alert.setContentText("���Ǹ� �����ּ���.");
			alert.showAndWait();
			return;
		}
		
		
		memberDao.signup(idStr, pwStr, nameStr);
		
		
		
		//Member member = new Member(idStr, pwStr, nameStr, mobileStr);
		//commonData.put(idStr, member);
		alert.setContentText("ȸ�����Կ� �����߽��ϴ�.");
		alert.showAndWait();
		open_login_form();
	}
	
	@FXML
	private void open_login_form() throws IOException {
		try {
			System.out.println("�α��� ȭ������ ���ư���");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/login.fxml"));
			AnchorPane pane = loader.load();
			
			//get the controller from the FXMLLoader
			LoginController controller = loader.getController();
			
			//set it in the controller
			
			Scene scene = new Scene(pane);
			
			//creatAccountButton�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
			Stage primaryStage = (Stage) createAccountButton.getScene().getWindow();
			
			primaryStage.setScene(scene);
			primaryStage.show();

			/*
			 * Timeline timeline = new Timeline(); KeyValue kv = new
			 * KeyValue(login.translateYProperty(), 0, Interpolator.EASE_IN); KeyFrame kf =
			 * new KeyFrame(Duration.seconds(1), kv); timeline.getKeyFrames().add(kf);
			 * 
			 * timeline.setOnFinished(event1 -> { login.getChildren().remove(container); });
			 * 
			 * timeline.play();
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * Parent root =
		 * FXMLLoader.load(getClass().getResource("/application/views/login.fxml"));
		 * 
		 * Scene scene = createAccountButton.getScene();
		 * 
		 * root.translateYProperty().set(scene.getHeight());
		 * 
		 * StackPane parentContainer = (StackPane) scene.getRoot();
		 * parentContainer.getChildren().add(root);
		 */

	}

}