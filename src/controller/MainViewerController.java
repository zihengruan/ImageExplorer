package controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import model.ImageFile;

public class MainViewerController extends RootController {
	
	private Stage viewerStage;
	
	private ImageFile imagefile;

    @FXML
    private ImageView image;
    
    @FXML
    private Button deleteButton;

    @FXML
    private Button rotateButton;

    @FXML
    private Button previousButton;
    
    @FXML
    private Button nextButton;

    @FXML
    private Button editButton;

    @FXML
    private Button slideButton;

    @FXML
    private Button detailsButton;

    @FXML
    private Button likeButton;

    @FXML
    private Button resetButton;
    
    @FXML
    private Button zoomOutButton;
    
    @FXML
    private Button zoomInButton;
    
    @FXML
    void showSlideWindow(MouseEvent event) {
    	RootController.controllers.get("controller.SlideController").getStage().setFullScreen(true);
    	((CanShowImage)RootController.controllers.get("controller.SlideController")).setImage(this.image.getImage());
    	RootController.controllers.get("controller.SlideController").getStage().show();
    }
    
    @FXML
    void showEditWIndow(MouseEvent event) {
    	((EditController)RootController.controllers.get("controller.EditController")).setImageView(this.image.getImage());
    	RootController.controllers.get("controller.EditController").getStage().show();
    }
    
    public void initialize(URL location, ResourceBundle resources) {
    	addScrollEvent();
    	addCtrlScrollEvent();
    }
    
    public void setImage(ImageFile imageFile) {
    	this.imagefile = imageFile;
    	Image t_image;
		try {
			t_image = new Image(imageFile.getImageFile().toURI().toURL().toString(), true);
			this.image.setImage(t_image);
			this.image.setFitWidth(model.Utilities.originalFitWidth);
			this.image.setFitHeight(model.Utilities.originalFitHeight);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    	
    }
    
    @FXML
    void getNextImage(MouseEvent event) {
    	ImageFile t_ImageFile = model.Utilities.getNextImageFile(this.imagefile);
    	if(t_ImageFile != null) {
    		this.setImage(t_ImageFile);
    	}
    }
    
    @FXML
    void getPriviousImage(MouseEvent event) {
    	ImageFile t_ImageFile = model.Utilities.getPriviousImageFile(this.imagefile);
    	if(t_ImageFile != null) {
    		this.setImage(t_ImageFile);
    	}
    }


    @FXML
    void rotateImage(MouseEvent event) {
    	model.Utilities.rotateImage();
    }

    @FXML
    void resetAllProperties(MouseEvent event) {
    	model.Utilities.resetAll();
    }
    
    @FXML
    void zoomInImage(MouseEvent event) {
    	model.Utilities.zoomInImage();
    }

    @FXML
    void zoomOutImage(MouseEvent event) {
    	model.Utilities.zoomOutImage();
    }
    
    @Override
	public Stage getStage() {
		return viewerStage;
	}

	@Override
	public void setStage(Stage viewerStage) {
		this.viewerStage = viewerStage;
	}

	public ImageView getImageView() {
		return image;
	}
    
	private void addScrollEvent() {
//		TODO to shift or zoom and move

//		this.viewerStage.getScene().addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
//			@Override
//			public void handle(ScrollEvent event) {
//				double deltaY = event.getDeltaY();
//				if(deltaY > 0) {
//					ImageFile t_ImageFile = model.Utilities.getPriviousImageFile(MainViewerController.this.imagefile);
//			    	if(t_ImageFile != null) {
//			    		MainViewerController.this.setImage(t_ImageFile);
//			    	}
//				} else {
//					ImageFile t_ImageFile = model.Utilities.getNextImageFile(MainViewerController.this.imagefile);
//			    	if(t_ImageFile != null) {
//			    		MainViewerController.this.setImage(t_ImageFile);
//			    	}
//				}
//				
//			}});
//		this.image.setOnScroll(new EventHandler<ScrollEvent>() {
//			@Override
//			public void handle(ScrollEvent event) {
//				double deltaY = event.getDeltaY();
//				if(deltaY > 0) {
//					ImageFile t_ImageFile = model.Utilities.getPriviousImageFile(MainViewerController.this.imagefile);
//			    	if(t_ImageFile != null) {
//			    		MainViewerController.this.setImage(t_ImageFile);
//			    	}
//				} else {
//					ImageFile t_ImageFile = model.Utilities.getNextImageFile(MainViewerController.this.imagefile);
//			    	if(t_ImageFile != null) {
//			    		MainViewerController.this.setImage(t_ImageFile);
//			    	}
//				}
//				
//			}});
	}
	
	private void addCtrlScrollEvent() {
//		TODO 
	}
}
