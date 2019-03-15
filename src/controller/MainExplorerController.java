package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ImageFile;
import model.ImageTreeView;

public class MainExplorerController extends RootController  {
	
	private Stage mainStage;
		
    @FXML
    private MenuBar menuBar;

    @FXML
    private TreeView<ImageFile> treeView;

    @FXML
    private FlowPane flowPane;
    

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		treeView = new ImageTreeView(treeView).getTreeView();
	}
	
	
	
	

	public FlowPane getFlowPane() {
		return flowPane;
	}

	public void clearFlowPane() {
		this.flowPane.getChildren().clear();
	}





	@Override
	public Stage getStage() {
		// TODO Auto-generated method stub
		return mainStage;
	}

	@Override
	public void setStage(Stage stage) {
		// TODO Auto-generated method stub

		this.mainStage = stage;
		this.mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
			}
		});
		
	}






}
