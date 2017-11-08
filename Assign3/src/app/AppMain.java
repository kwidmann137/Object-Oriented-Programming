package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import controllers.PlanetController;

public class AppMain extends Application {

	public AppMain() {
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	//FXML startup method
	@Override
	public void start(Stage primaryStage) throws Exception {

		PlanetController controller = new PlanetController(primaryStage);

		FXMLLoader loader = new FXMLLoader(controller.getClass().getResource("/views/PlanetView.fxml"));
		loader.setController(controller);
		
		Pane pane = (Pane) loader.load();

		controller.initializeComponents();

		Scene scene = new Scene(pane, 650, 460);
		primaryStage.setTitle("CS 4773 Assignment 3");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}