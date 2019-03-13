package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import model.ImageFile;
import model.ImageTreeView;

public class MainExplorerController extends RootController  {
		
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



}
