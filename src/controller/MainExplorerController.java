package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import action.CopyAction;
import action.DeleteAction;
import action.PasteAction;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ImageFile;
import model.ImageLabel;
import model.ImageTreeView;
import model.PanelListener;
import model.RightClickMenu;
import model.Utilities;

public class MainExplorerController extends RootController  {
	
	private Stage mainStage;
		
	public static String theFilePath;
	
	public static String diretoryName;
	
    @FXML
    private AnchorPane parentPane;
	
	private MainExplorerController mainExplorerController;
	
	private ArrayList<ImageLabel> pictures;
	
	private ArrayList<File> files;
    @FXML
    private TreeView<ImageFile> treeView;
    
    @FXML
    private Rectangle rect;

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
		new PanelListener(flowPane, mainExplorerController, rect);
		
		new RightClickMenu(flowPane, mainExplorerController, false);
		
		this.copyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				new CopyAction();
			}
		});
		
		this.pasteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				new PasteAction(mainExplorerController);
			}
		});
		
		
		this.deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				new DeleteAction(mainExplorerController);
			}
		
		
			
		});
		
		
		this.parentPane.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				MainExplorerController.this.flowPane.setPrefWidth((double)newValue-MainExplorerController.this.treeView.getWidth()-10);
//				MainExplorerController.this.flowPane.setMinHeight(MainExplorerController.this.parentPane.getHeight());
				
			}

			
			
		});
		
		
	}
	
    @FXML
    void openImage() {
    	((MainViewerController) RootController.controllers.get("controller.MainViewerController"))
		.setImage(model.Utilities.selectedImageFiles.get(0));
    	if (!RootController.controllers.get("controller.MainViewerController").getStage().isShowing()) {
			((MainViewerController) RootController.controllers.get("controller.MainViewerController"))
					.showStage();
		}
    }
//    void openFile(MouseEvent event) {
//		((MainViewerController)RootController.controllers.get("controller.MainViewerController")).setImage(new ImageFile(Utilities.selectedfiles.get(0)));
//		if(!RootController.controllers.get("controller.MainViewerController").getStage().isShowing()) {
//			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).showStage();;
//		}
//    }
	
    @FXML
    void showEditWindow(MouseEvent event) {
		((EditController) RootController.controllers.get("controller.EditController"))
		.setImage(new ImageFile(Utilities.selectedfiles.get(0)));
		((EditController)RootController.controllers.get("controller.EditController")).showStage();
    }
    
    @FXML
    void showSlide(MouseEvent event) {
		RootController.controllers.get("controller.SlideController").getStage().setFullScreen(true);
		((SlideController) RootController.controllers.get("controller.SlideController")).setImage(new ImageFile(Utilities.selectedfiles.get(0)));
		((SlideController) RootController.controllers.get("controller.SlideController")).showStage();
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
