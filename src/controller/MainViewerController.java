package controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.ImageFile;

public class MainViewerController extends RootController {
	
	private Stage viewerStage;

    @FXML
    private Button next;

    @FXML
    private ImageView image;
    
    @FXML
    private Button deleteButton;

    @FXML
    private Button rotateButton;

    @FXML
    private Button previous;

    @FXML
    private Button editButton;

    @FXML
    private Button slideButton;

    @FXML
    private Button zoomButton;

    @FXML
    private Button detailsButton;

    @FXML
    private Button likeButton;
    
    public void initialize(URL location, ResourceBundle resources) {		
    }
    
    public void setImage(ImageFile imageFile) {
    	//TODO 
    	Image t_image;
		try {
//			t_image = new Image(imageFile.getImageFile().toURI().toURL().toString(), 600, 400, true, true, true);

			t_image = new Image(imageFile.getImageFile().toURI().toURL().toString(), true);
			this.image.setImage(t_image);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

	public Stage getViewerStage() {
		return viewerStage;
	}

	public void setViewerStage(Stage viewerStage) {
		this.viewerStage = viewerStage;
	}
    

}
