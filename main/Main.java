package application.main;
	
import application.controllers.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/start.fxml"));
		StackPane pane = loader.load();
		
		//get the controller from the FXMLLoader
		StartController controller = loader.getController();
		
		//set it in the controller ºº≈Õ
		
		
		
		Scene scene = new Scene(pane);
		primaryStage.setTitle("Together");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}