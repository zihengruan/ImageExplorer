package controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ImageFile;

public class MainViewerController extends RootController {

	private Stage viewerStage;

	private ImageFile imagefile;

	private double oldScreenX;

	private double oldScreenY;

	private double translateX = 0;

	private double translateY = 0;
	
    @FXML
    private Label fileSize;

    @FXML
    private Label fileDate;

    @FXML
    private Label fileResolution;

    @FXML
    private Label filePath;

    @FXML
    private Label fileName;
    
    @FXML
    private ColumnConstraints detailsFrame;

	@FXML
	private BorderPane controllPane;

	@FXML
	private ImageView image;

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
	private Button resetButton;

	@FXML
	private Button zoomOutButton;

	@FXML
	private Button zoomInButton;
	
	private Timeline showDetails;
	private Timeline closeDetails;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addImageDragEvent();
		addScrollEvent();
		addDirectionKeyEvent();
		
		this.showDetails = new Timeline();
		this.showDetails.setAutoReverse(true);
		KeyValue kv = new KeyValue(this.detailsFrame.prefWidthProperty(), 450);
		KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
		this.showDetails.getKeyFrames().add(kf);
		
		this.closeDetails = new Timeline();
		this.closeDetails.setAutoReverse(true);
		KeyValue kv2 = new KeyValue(this.detailsFrame.prefWidthProperty(), 0);
		KeyFrame kf2 = new KeyFrame(Duration.millis(500), kv2);
		this.closeDetails.getKeyFrames().add(kf2);
	}

	@FXML
	void showSlideWindow(MouseEvent event) {
		RootController.controllers.get("controller.SlideController").getStage().setFullScreen(true);
		((CanShowImage) RootController.controllers.get("controller.SlideController")).setImage(this.image.getImage());
		((SlideController) RootController.controllers.get("controller.SlideController")).showStage();

	}

	@FXML
	void showEditWIndow(MouseEvent event) {
		((EditController) RootController.controllers.get("controller.EditController"))
				.setImage(this.imagefile);
		((EditController)RootController.controllers.get("controller.EditController")).showStage();
	}

	public void setImage(ImageFile imageFile) {
		
		this.fileDate.setText(imageFile.getImageLastModifiedDate());
		this.fileName.setText(imageFile.getImageName());
		this.filePath.setText(imageFile.getImagePath());
		this.fileSize.setText(imageFile.getImageSize());
		this.fileResolution.setText(imageFile.getResolution());
		this.imagefile = imageFile;
		Image t_image;
		try {
			t_image = new Image(imageFile.getImageFile().toURI().toURL().toString(), true);
			this.image.setImage(t_image);
			model.Utilities.resetAll();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void getNextImage(MouseEvent event) {
		this.setNextImage();
	}

	@FXML
	void getPriviousImage(MouseEvent event) {
		this.setPriviousImage();
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
		this.controllPane.addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				double deltaY = event.getDeltaY();
				if (event.isControlDown()) {
					if (deltaY > 0) {
						model.Utilities.zoomInImage();
					} else {
						model.Utilities.zoomOutImage();
					}
				} else {
					if (deltaY > 0) {
						setPriviousImage();
					} else {
						setNextImage();
					}
					model.Utilities.resetAll();
				}
			}
		});
	}

	private void addDirectionKeyEvent() {
		this.controllPane.setOnKeyPressed(new EventHandler<KeyEvent>() {

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

	private void addImageDragEvent() {
		this.controllPane.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
					oldScreenX = event.getScreenX();
					oldScreenY = event.getScreenY();
				} else if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
					MainViewerController.this.image
							.setTranslateX(translateX + event.getScreenX() - MainViewerController.this.oldScreenX);
					MainViewerController.this.image
							.setTranslateY(translateY + event.getScreenY() - MainViewerController.this.oldScreenY);
				} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
					translateX += event.getScreenX() - MainViewerController.this.oldScreenX;
					translateY += event.getScreenY() - MainViewerController.this.oldScreenY;
				}
			}
		});
	}
	
    @FXML
    void toggleDetails(ActionEvent event) {
    	if(MainViewerController.this.detailsFrame.getPrefWidth() == 0) {
    		this.showDetails.play();
    		this.fileSize.setEllipsisString("...");
    		this.fileDate.setEllipsisString("...");
    		this.fileResolution.setEllipsisString("...");
    		this.filePath.setEllipsisString("...");
    		this.fileName.setEllipsisString("...");
    	}
    	else {
    		this.closeDetails.play();
    		this.fileSize.setEllipsisString("");
    		this.fileDate.setEllipsisString("");
    		this.fileResolution.setEllipsisString("");
    		this.filePath.setEllipsisString("");
    		this.fileName.setEllipsisString("");
    	}
    }

	public void resetImagePosition() {
		MainViewerController.this.image.setTranslateX(0);
		MainViewerController.this.image.setTranslateY(0);
		translateX = 0;
		translateY = 0;
	}

	public void showStage() {

		detailsFrame.setPrefWidth(0);
		this.fileSize.setEllipsisString("");
		this.fileDate.setEllipsisString("");
		this.fileResolution.setEllipsisString("");
		this.filePath.setEllipsisString("");
		this.fileName.setEllipsisString("");
		this.viewerStage.show();
	}

}
