package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.dao.BoardDAO;
import application.util.SessionData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NewWriteController implements Initializable {
	@FXML private TextField title;
	@FXML private PasswordField pass;
	@FXML private ComboBox<String> publicCombo;
	@FXML private DatePicker endDate;
	@FXML private TextArea content;
	@FXML private Label msg;
	@FXML private ListView<String> listView;
	@FXML private ComboBox<String> selector;
	@FXML private Button cancelBtn;
	@FXML private Button insertBtn;
	@FXML private Label currentCategoryId;

	private int member_id;
	private int group_id;
	private BoardDAO boardDao = new BoardDAO();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("FXML LOAD COMPLITE!!");
		member_id = SessionData.getSessionData().get("id");
		group_id = SessionData.getSessionData().get("group_id");
		
	}
	
	//버튼 클릭시 하는 행동들 
	public void save() throws Exception { 
		System.out.println("제목 : " + title.getText());
		System.out.println("내용 : " + content.getText());
		System.out.println("카테고리id : "+ currentCategoryId.getText());
		System.out.println("멤버 : "+member_id);
		System.out.println("그룹 : "+group_id);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText("알림 메시지");
		
		boardDao.write_board(member_id, group_id, Integer.parseInt(currentCategoryId.getText()), title.getText(), content.getText());
		
		alert.setContentText("게시글이 등록되었습니다.");
		alert.showAndWait();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/list.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		ListController controller = loader.getController();
		
		//set it in the controller
	
		
		Scene scene = new Scene(pane);
		
		//registerBtn은 기존 Stage를 재활용하기 위한 접근수단
		Stage primaryStage = (Stage) insertBtn.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	//취소 버튼
	public void cancel(ActionEvent event) throws Exception {
		System.out.println("게시글 작성 취소 요청");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/list.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		ListController controller = loader.getController();
		
		//set it in the controller
		
		
		Scene scene = new Scene(pane);
		
		//registerBtn은 기존 Stage를 재활용하기 위한 접근수단
		Stage primaryStage = (Stage) cancelBtn.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void setCurrentCategoryId(String currentCategoryId) {
		this.currentCategoryId.setText(currentCategoryId);
	}
	
	
}