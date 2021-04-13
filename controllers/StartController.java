package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartController implements Initializable {

    @FXML
    private StackPane parentContainer;
    @FXML
    private AnchorPane container;
    @FXML
    private Button getStartedButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void open_login(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/login.fxml"));
		AnchorPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		LoginController controller = loader.getController();
		
		//set it in the controller 세터
		
		Scene scene = new Scene(pane);
		
		//registerBtn은 기존 Stage를 재활용하기 위한 접근수단
		Stage primaryStage = (Stage) getStartedButton.getScene().getWindow();
				
		primaryStage.setScene(scene);
		primaryStage.show();
		
		/*
		 * Timeline timeline = new Timeline(); KeyValue kv = new
		 * KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN); KeyFrame kf =
		 * new KeyFrame(Duration.seconds(1), kv); timeline.getKeyFrames().add(kf);
		 * timeline.setOnFinished(event1 -> {
		 * parentContainer.getChildren().remove(container); }); timeline.play();
		 */

    }

}
