package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.ImageFile;

public class MainViewerController extends RootController {

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
    	
    }
    

}
