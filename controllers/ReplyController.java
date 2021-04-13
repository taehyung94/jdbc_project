package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

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

public class ReplyController implements Initializable {
	
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("FXML LOAD COMPLITE!!");
		/*
		 * ObservableList<String> combo = FXCollections.observableArrayList("����",
		 * "ȸ������","�����"); publicCombo.setItems(combo);
		 */
		
		
	}
	
	//��ư Ŭ���� �ϴ� �ൿ�� 
	public void save() throws Exception { 
		System.out.println("���� : " + title.getText());
		/* System.out.println("��й�ȣ :  " + pass.getText()); */
		/* System.out.println("�������� : " + publicCombo.getValue()); */
		/* System.out.println("�Խ����� : " + endDate.getValue()); */
		System.out.println("���� : " + content.getText());
		
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText("�˸� �޽���");
		
		
		// ���̵� �Է� �˻�
		String titleStr = title.getText();
		if(titleStr.isEmpty()) {
			alert.setContentText("������ �Է��� �ּ���.");
			alert.showAndWait();
			return;
		}
		
		alert.setContentText("����� ��ϵǾ����ϴ�.");
		alert.showAndWait();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/list.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		ListController controller = loader.getController();
		
		//set it in the controller

		
		Scene scene = new Scene(pane);
		
		//registerBtn�� ���� Stage�� ��Ȱ���ϱ� ���� ���ټ���
		Stage primaryStage = (Stage) insertBtn.getScene().getWindow();
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	//��� ��ư
	public void cancel(ActionEvent event) throws Exception {
		System.out.println("��� �ۼ� ��� ��û");
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
	
	public void setCurrentCategoryId(String currentCategoryId) {
		this.currentCategoryId.setText(currentCategoryId);
	}
	
	
	
}