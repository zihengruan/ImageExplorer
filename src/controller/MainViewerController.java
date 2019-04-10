package controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import model.ImageFile;

public class MainViewerController extends RootController {

	private Stage viewerStage;

	private ImageFile imagefile;
	
	private double oldX;
	
	private double oldY;
	
	private double oldScreenX;
	
	private double oldScreenY;

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
		((CanShowImage) RootController.controllers.get("controller.SlideController")).setImage(this.image.getImage());
		((SlideController) RootController.controllers.get("controller.SlideController")).showStage();

	}

	@FXML
	void showEditWIndow(MouseEvent event) {
		((EditController) RootController.controllers.get("controller.EditController"))
				.setImageView(this.image.getImage());
		RootController.controllers.get("controller.EditController").getStage().show();
	}

	public void initialize(URL location, ResourceBundle resources) {
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
		if (t_ImageFile != null) {
			this.setImage(t_ImageFile);
		}
	}

	@FXML
	void getPriviousImage(MouseEvent event) {
		ImageFile t_ImageFile = model.Utilities.getPriviousImageFile(this.imagefile);
		if (t_ImageFile != null) {
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

	public ImageFile getImagefile() {
		return imagefile;
	}

	private void addScrollEvent() {
		this.viewerStage.getScene().addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				double deltaY = event.getDeltaY();
				if(event.isControlDown()) {
					
					if (deltaY > 0) {
						model.Utilities.zoomInImage();
					} else {
						model.Utilities.zoomOutImage();
					}
				}else {
					if (deltaY > 0) {
						setPriviousImage();
					} else {
						setNextImage();
					}
				}
				

			}
		});
	}

	private void addDirectionKeyEvent() {
		this.viewerStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.UP)) {
					setPriviousImage();
				}
				if (event.getCode().equals(KeyCode.DOWN)) {
					setNextImage();
				}
				if (event.getCode().equals(KeyCode.LEFT)) {
					setPriviousImage();
				}
				if (event.getCode().equals(KeyCode.RIGHT)) {
					setNextImage();
				}
			}
		});
	}

	private void setPriviousImage() {
		ImageFile t_ImageFile = model.Utilities.getPriviousImageFile(MainViewerController.this.imagefile);
		if (t_ImageFile != null) {
			MainViewerController.this.setImage(t_ImageFile);
		}
	}

	private void setNextImage() {
		ImageFile t_ImageFile = model.Utilities.getNextImageFile(MainViewerController.this.imagefile);
		if (t_ImageFile != null) {
			MainViewerController.this.setImage(t_ImageFile);
		}
	}
	
//	private void addImageDragEvent() {
//		this.viewerStage.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
////				TODO	
//				if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
//					oldX = MainViewerController.this.image.getX();
//					oldY = MainViewerController.this.image.getY();
//					oldScreenX = event.getScreenX();
//					oldScreenY = event.getScreenY();
//					System.out.println("old: " + oldX + oldY + oldScreenX + oldScreenY);
//				}
//				if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
//					MainViewerController.this.image.setX(MainViewerController.this.oldX + MainViewerController.this.oldScreenX - event.getScreenX());
//					MainViewerController.this.image.setY(MainViewerController.this.oldY + MainViewerController.this.oldScreenY - event.getScreenY());
//					System.out.println("new: " + MainViewerController.this.image.getX() + MainViewerController.this.image.getY() + event.getScreenX() + event.getScreenY());
//				}
//			}
//		});
//	}


	public void showStage() {
//		addImageDragEvent();
		addScrollEvent();
		addDirectionKeyEvent();
		this.viewerStage.show();
	}

}
