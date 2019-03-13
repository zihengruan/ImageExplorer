package application;
	
import controller.MainViewerController;
import controller.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/MainExplorer.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Parent root2 = FXMLLoader.load(getClass().getResource("../view/MainViewer.fxml"));
			Scene scene2 = new Scene(root2);
			Stage viewerStage = new Stage();
			viewerStage.setScene(scene2);
			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).setViewerStage(viewerStage);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
