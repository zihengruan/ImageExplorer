package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ImageFile;
import model.ImageTreeView;

public class MainExplorerController extends RootController  {
	
	private Stage mainStage;
		

    @FXML
    private TreeView<ImageFile> treeView;

    @FXML
    private FlowPane flowPane;
    
    @FXML
    private Text dateText;

    @FXML
    private Text resText;

    @FXML
    private Text pathText;
    
    @FXML
    private Text sizeText;

    @FXML
    private Text nameText;
    
    @FXML
    private Text amountText;
    
    @FXML
    private Text statusText;
    
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
		//主窗体关闭事件
		this.mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				Platform.exit(); //退出程序
			}
		});
		
	}
	
	public void setDateText(String text) {
		this.dateText.setText(text);
	}

	public void setResText(String text) {
		this.resText.setText(text);
	}

	public void setPathText(String text) {
		this.pathText.setText(text);
	}

	public void setSizeText(String text) {
		this.sizeText.setText(text);
	}

	public void setNameText(String text) {
		this.nameText.setText(text);
	}

	public void setAmountText(String text) {
		this.amountText.setText(text);
	}

	public void setStatusText(String text) {
		this.statusText.setText(text);
	}

	




}
