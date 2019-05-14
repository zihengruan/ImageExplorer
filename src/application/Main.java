package application;
	
import java.io.IOException;

import controller.MainViewerController;
import controller.RootController;
import controller.SlideController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	public static Window mainStage;
	@Override
	public void start(Stage primaryStage) {
		if(System.getProperty("os.arch").endsWith("64")) {
			System.loadLibrary("opencv_java410_64");
		}else {
			System.loadLibrary("opencv_java410_86");
		}
		
		try {
			//准备好所有窗口
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
//			scene.setFill(null);
			Stage stage = new Stage();
//			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setScene(scene);
			stage.setTitle("ImageExplorer");
			stage.getIcons().add(new Image("/view/icon.jpg"));
			RootController.controllers.get(controllerName).setStage(stage);
			if(isShow) {
				stage.setMaximized(true);
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
