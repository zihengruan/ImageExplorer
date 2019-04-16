package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditController extends RootController{
	
	private Stage editStage;
	
	@FXML
	private TabPane functionPane;
	
    @FXML
    private Button rotateButton;

    @FXML
    private Button filterButton;
    
    @FXML
    private Button adjustButton;
    
    @FXML
    private Text functionTabName;

    @FXML
    private Button cutAndRotate;

    @FXML
    private Button undoButtton;

    @FXML
    private Button saveCopyButton;

    @FXML
    private Button zoomOutButton;

    @FXML
    private ImageView imageView;

    @FXML
    private Button saveButton;

    @FXML
    private Button zoomInButton;
    
    public void setImageView(Image image) {
		this.imageView.setImage(image);
	}
    
    public Image getImage() {
    	return this.imageView.getImage();
    }

	@Override
	public Stage getStage() {
		return this.editStage;
	}

	@Override
	public void setStage(Stage stage) {
		this.editStage = stage;
		
	}
	
	private void selectFuntionPane(int index) {
		this.functionPane.getSelectionModel().select(index);;
	}
	
	private void addFuntionSelection() {
		this.rotateButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectFuntionPane(0);
				EditController.this.functionTabName.setText("裁剪与旋转");
			}
		});
		
		this.filterButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectFuntionPane(1);
				EditController.this.functionTabName.setText("滤镜");
			}
		});
		
		this.adjustButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectFuntionPane(2);
				EditController.this.functionTabName.setText("调整");
			}
		});
	}

	public void showStage() {
		addFuntionSelection();
		this.editStage.show();
	}
}

