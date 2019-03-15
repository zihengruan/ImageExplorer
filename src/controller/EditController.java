package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EditController extends RootController{
	
	private Stage editStage;

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

}

