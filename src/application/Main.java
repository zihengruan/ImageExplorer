package application;
	
import java.io.IOException;

import controller.MainViewerController;
import controller.RootController;
import controller.SlideController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			Parent root = FXMLLoader.load(getClass().getResource("../view/MainExplorer.fxml"));
//			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
//			
//			root = FXMLLoader.load(getClass().getResource("../view/MainViewer.fxml"));
//			Scene viewerScene = new Scene(root);
//			Stage viewerStage = new Stage();
//			viewerStage.setScene(viewerScene);
//			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).setViewerStage(viewerStage);
//			
//			root = FXMLLoader.load(getClass().getResource("../view/Slide.fxml"));
//			Scene slideScene = new Scene(root);
//			Stage slideStage = new Stage();
//			slideStage.setScene(slideScene);
//			((SlideController)RootController.controllers.get("controller.SlideController")).setSlideStage(slideStage);
//			
			createStage("../view/MainExplorer.fxml", "controller.MainExplorerController", true);
			createStage("../view/MainViewer.fxml", "controller.MainViewerController", false);
			createStage("../view/Slide.fxml", "controller.SlideController", false);
			createStage("../view/Edit.fxml", "controller.EditController", false);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createStage(String fxmlPath, String controllerName, boolean isShow) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
//			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setScene(scene);
			stage.setTitle("ImageExplorer");
			stage.getIcons().add(new Image("/view/icon.jpg"));
			RootController.controllers.get(controllerName).setStage(stage);
			if(isShow) {
				stage.show();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
