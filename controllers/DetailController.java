package application.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.dao.BoardDAO;
import application.util.SessionData;
import application.vo.BoardVO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DetailController implements Initializable{
	
	
	@FXML private Label writer;
	@FXML private Button cancelBtn;
	@FXML private Button updateBtn;
	@FXML private Button deleteBtn;
	@FXML private Button replyBtn;
	@FXML private TextArea content;
	@FXML private TextField title;
	@FXML private Label currentCategoryId;
	@FXML private Label boardId;
	@FXML private Button updateCompleteBtn;
	
	
	private BoardDAO boardDao = new BoardDAO();
	private int member_id;
	private int group_id;
	private BoardVO boardvo;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		member_id=SessionData.getSessionData().get("id");
		group_id=SessionData.getSessionData().get("group_id");
		
		
	}
	
	public void cancel() throws Exception {
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
	
	public void update() {
		boolean flag = boardDao.board_owner_check(Integer.parseInt(boardId.getText()), member_id, group_id);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText("알림 메시지");
		if(flag == false) {
			alert.setHeaderText("수정 실패");
			alert.setContentText("작성자만 글을 수정할 수 있습니다.");
			alert.showAndWait();
			return;
		}
		alert.setAlertType(AlertType.CONFIRMATION);
		alert.setTitle("알림창");
		alert.setHeaderText("알림 메시지");
		alert.setContentText("게시글을 수정하시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			content.setEditable(true);
			title.setEditable(true);
			updateBtn.setVisible(false);
			updateCompleteBtn.setVisible(true);
			
		}
			
		
		
	}
	public void updateComplete() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("알림창");
		alert.setHeaderText("알림 메시지");
		alert.setContentText("수정을 마치시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			boolean flag = boardDao.edit_board(Integer.parseInt(boardId.getText()), member_id, group_id, title.getText(), content.getText());
			alert.setAlertType(AlertType.INFORMATION);
			if(flag == false) {
				alert.setContentText("수정 실패");
			}
			else {
				alert.setContentText("수정 완료");
			}
			alert.showAndWait();
			content.setEditable(false);
			title.setEditable(false);
			updateBtn.setVisible(true);
			updateCompleteBtn.setVisible(false);
			
		}
		
	}
	
	public void delete() throws Exception {
		boolean flag = boardDao.board_owner_check(Integer.parseInt(boardId.getText()), member_id, group_id);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText("알림 메시지");
		if(flag == false) {
			alert.setHeaderText("삭제 실패");
			alert.setContentText("작성자만 글을 삭제할 수 있습니다.");
			alert.showAndWait();
			return;
		}
		alert.setAlertType(AlertType.CONFIRMATION);
		alert.setTitle("알림창");
		alert.setHeaderText("알림 메시지");
		alert.setContentText("정말 삭제하시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			flag = boardDao.delete_board(member_id, Integer.parseInt(boardId.getText()), group_id);
			if(flag==false) {
				alert.setAlertType(AlertType.INFORMATION);
			    alert.setContentText("삭제 실패.");
			    alert.showAndWait();
			    return;
			}
		    alert.setAlertType(AlertType.INFORMATION);
		    alert.setContentText("글이 삭제되었습니다.");
		    alert.showAndWait();
		    
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/list.fxml"));
		    AnchorPane pane = loader.load();
			
			//get the controller from the FXMLLoader
			ListController controller = loader.getController();
			
			//set it in the controller
			
			Scene scene = new Scene(pane);
			
			//registerBtn은 기존 Stage를 재활용하기 위한 접근수단
			Stage primaryStage = (Stage) deleteBtn.getScene().getWindow();
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		else {
		    return;
		}

	}
	
	public void reply() throws Exception {
		System.out.println("답글작성 시도");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/reply.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		ReplyController controller = loader.getController();
		
		//set it in the controller
		
		Scene scene = new Scene(pane);
		
		//registerBtn은 기존 Stage를 재활용하기 위한 접근수단
		Stage primaryStage = (Stage) replyBtn.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	

    
    public void setCurrentCategoryId(String currentCategoryId) {
		this.currentCategoryId.setText(currentCategoryId);
	}
    
	public void setBoardId(String boardId) {
		this.boardId.setText(boardId);
		boardvo = boardDao.read_board_detail(Integer.parseInt(this.boardId.getText()));
		writer.setText(boardvo.getWriter_name());
		content.setText(boardvo.getContent());
		title.setText(boardvo.getTitle());
	}
	
}
