package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import action.CopyAction;
import action.DeleteAction;
import action.PasteAction;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ImageFile;
import model.ImageLabel;
import model.ImageTreeView;
import model.PanelListener;
import model.RightClickMenu;

public class MainExplorerController extends RootController  {
	
	private Stage mainStage;
		
	public static String theFilePath;
	
	private MainExplorerController mainExplorerController;
	
	private ArrayList<ImageLabel> pictures;
	
	private ArrayList<File> files;
    @FXML
    private TreeView<ImageFile> treeView;

    @FXML
    private FlowPane flowPane;
    
    @FXML
    private Button openButton;

    @FXML
    private Button editButton;
    
    @FXML
    private Button copyButton;

    @FXML
    private Button pasteButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button slideButton;
    
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
		new PanelListener(flowPane,mainExplorerController);
		
		new RightClickMenu(flowPane, mainExplorerController, false);
		
		this.copyButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				new CopyAction();
			}
		});
		
		this.pasteButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				new PasteAction(mainExplorerController);
			}
		});
		
		
		this.deleteButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				new DeleteAction(mainExplorerController);
			}
		});
		
//		TODO cut button
		
//		TODO open edit 

//		TODO　open　silde
		
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
