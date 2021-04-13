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
		
		//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
		Stage primaryStage = (Stage) cancelBtn.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void update() {
		boolean flag = boardDao.board_owner_check(Integer.parseInt(boardId.getText()), member_id, group_id);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("�˸� �޽���");
		if(flag == false) {
			alert.setHeaderText("���� ����");
			alert.setContentText("�ۼ��ڸ� ���� ������ �� �ֽ��ϴ�.");
			alert.showAndWait();
			return;
		}
		alert.setAlertType(AlertType.CONFIRMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("�˸� �޽���");
		alert.setContentText("�Խñ��� �����Ͻðڽ��ϱ�?");
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
		alert.setTitle("�˸�â");
		alert.setHeaderText("�˸� �޽���");
		alert.setContentText("������ ��ġ�ðڽ��ϱ�?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			boolean flag = boardDao.edit_board(Integer.parseInt(boardId.getText()), member_id, group_id, title.getText(), content.getText());
			alert.setAlertType(AlertType.INFORMATION);
			if(flag == false) {
				alert.setContentText("���� ����");
			}
			else {
				alert.setContentText("���� �Ϸ�");
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
		alert.setTitle("�˸�â");
		alert.setHeaderText("�˸� �޽���");
		if(flag == false) {
			alert.setHeaderText("���� ����");
			alert.setContentText("�ۼ��ڸ� ���� ������ �� �ֽ��ϴ�.");
			alert.showAndWait();
			return;
		}
		alert.setAlertType(AlertType.CONFIRMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("�˸� �޽���");
		alert.setContentText("���� �����Ͻðڽ��ϱ�?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			flag = boardDao.delete_board(member_id, Integer.parseInt(boardId.getText()), group_id);
			if(flag==false) {
				alert.setAlertType(AlertType.INFORMATION);
			    alert.setContentText("���� ����.");
			    alert.showAndWait();
			    return;
			}
		    alert.setAlertType(AlertType.INFORMATION);
		    alert.setContentText("���� �����Ǿ����ϴ�.");
		    alert.showAndWait();
		    
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/list.fxml"));
		    AnchorPane pane = loader.load();
			
			//get the controller from the FXMLLoader
			ListController controller = loader.getController();
			
			//set it in the controller
			
			Scene scene = new Scene(pane);
			
			//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
			Stage primaryStage = (Stage) deleteBtn.getScene().getWindow();
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		else {
		    return;
		}

	}
	
	public void reply() throws Exception {
		System.out.println("����ۼ� �õ�");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/reply.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		ReplyController controller = loader.getController();
		
		//set it in the controller
		
		Scene scene = new Scene(pane);
		
		//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
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
