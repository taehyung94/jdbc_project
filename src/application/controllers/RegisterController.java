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
		alert.setTitle("알림창");
		alert.setHeaderText("알림 메시지");
		if(!idStr.matches(e_pattern)) {
			alert.setContentText("Email 형식에 맞지 않습니다.");
			alert.showAndWait();
			return;
		}
		if(idStr.equals("")) {
			// 아이디를 입력해 주세요. 팝업창을 보여준다.
			
			alert.setContentText("Email을 입력해 주세요.");
			alert.showAndWait();
			//.showPopup(stage, "아이디를 입력해 주세요.");
			return;
		}
		
		boolean check = memberDao.email_check(idStr);
		
		if(check==false) {//commonData.exists(idStr)) {
			// 아이디 중복(다른 아이디를 사용하도록 알림)을 사용자에게 알린다.
			alert.setContentText("이미 가입한 Email 입니다.");
			alert.showAndWait();
			idDuplicate = false;
			return;
		}
		alert.setContentText("사용 가능한 Email 입니다.");
		alert.showAndWait();
		//dialogCommon.showPopup(stage, "사용 가능한 아이디 입니다.");
		idDuplicate = true;
	}

	@FXML
	public void handleSignup() throws IOException {
		System.out.println("SignUpController:handleSignup()");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText("알림 메시지");
		// 이름 입력 검사
		String nameStr = name.getText();
		if(nameStr.isEmpty()) {
			alert.setContentText("이름을 입력해 주세요.");
			alert.showAndWait();
			return;
		}
		
		// 아이디 입력 검사
		String idStr = email.getText();
		if(idStr.isEmpty()) {
			alert.setContentText("Email을 입력해 주세요.");
			alert.showAndWait();
			return;
		}
		
		// 비밀번호 입력 검사
		String pwStr = pwd.getText();
		if(pwStr.isEmpty()) {
			alert.setContentText("비밀번호를 입력해 주세요.");
			alert.showAndWait();
			return;
		}
		
		// 비밀번호 확인 입력 검사
		String pwConfirmStr = cpwd.getText();
		if(pwConfirmStr.isEmpty()) {
			alert.setContentText("비밀번호 확인을 입력해 주세요.");
			alert.showAndWait();
			return;
		}
		
		// 비밀번호와 비밀번호 확인이 일치하는지 검사
		if(!pwStr.equals(pwConfirmStr)) {
			alert.setContentText("비밀번호가 일치해야 합니다.");
			alert.showAndWait();
			cpwd.setText("");
			return;
		}
		
		if(idDuplicate == false) {
			alert.setContentText("Email 중복 확인을 해주세요.");
			alert.showAndWait();
			return;
		}
		
		
		System.out.println(agree);
		if(!agree.isSelected()) {
			alert.setContentText("동의를 눌러주세요.");
			alert.showAndWait();
			return;
		}
		
		
		memberDao.signup(idStr, pwStr, nameStr);
		
		
		
		//Member member = new Member(idStr, pwStr, nameStr, mobileStr);
		//commonData.put(idStr, member);
		alert.setContentText("회원가입에 성공했습니다.");
		alert.showAndWait();
		open_login_form();
	}
	
	@FXML
	private void open_login_form() throws IOException {
		try {
			System.out.println("로그인 화면으로 돌아가기");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/login.fxml"));
			AnchorPane pane = loader.load();
			
			//get the controller from the FXMLLoader
			LoginController controller = loader.getController();
			
			//set it in the controller
			
			Scene scene = new Scene(pane);
			
			//creatAccountButton은 기존 Stage를 재활용하기 위한 접근수단
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