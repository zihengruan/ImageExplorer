package controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ImageFile;

public class SlideController extends RootController implements CanShowImage {
	
	private Timeline timeline;

	private Duration duration = Duration.seconds(2);

	private int i;

	private int currentIndex;

	private Stage slideStage;
	
    @FXML
    private GridPane bottomBar;
	
    @FXML
    private ToggleButton playPauseButton;

	@FXML
	private ImageView image;

	@FXML
	private Button confirmAndPlayButton;

	@FXML
	private TextField delayTime;

	public void initialize(URL location, ResourceBundle resources) {
		this.bottomBar.setVisible(false);
	}

	
    @FXML
    void confirmAndPlay(MouseEvent event) {
    	if(delayTime.getText() != null && !delayTime.getText().isEmpty()) {
    		i = currentIndex + 1;
    		timeline.stop();
    		duration = Duration.seconds(Double.parseDouble(delayTime.getText()));
    		newTimeline();
    		timeline.play();
    	}
    } 

    @FXML
    void playPause(MouseEvent event) {
    	if(this.playPauseButton.isSelected()) {
    		timeline.play();
//    		playPauseButton.setText("Pause");
    	}else {
    		timeline.pause();
//    		playPauseButton.setText("Play");
    	}
    }
    
    @FXML
    void mouseEnteredBottomBar(MouseEvent event) {
    	this.bottomBar.setVisible(true);
    }

    @FXML
    void mouseExitedBottomBar(MouseEvent event) {
    	this.bottomBar.setVisible(false);
    }
    

	@Override
	public Stage getStage() {
		return this.slideStage;
	}

	@Override
	public void setStage(Stage slideStage) {
		slideStage.setFullScreen(true);
		this.slideStage = slideStage;
		this.slideStage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == false) {
					SlideController.this.getStage().hide();
					i = currentIndex + 1;
					timeline.stop();
				}

			}

		});
	}

	@Override
	public void setImage(Image image) {
		this.image.setImage(image);
	}
	
	

	@Override
	public void setImage(ImageFile imageFile) {
		Image image = null;
		try {
			image = new Image(imageFile.getImageFile().toURI().toURL().toString(), true);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setImage(image);
	}

	private void newTimeline() {
		ImageFile t_imageFile = ((MainViewerController) RootController.controllers
				.get("controller.MainViewerController")).getImagefile();
//		TODO should be selected imageFileList
		currentIndex = model.Utilities.imageFileList.indexOf(t_imageFile);
		i = currentIndex + 1;

		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);

		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (i == currentIndex) {
					timeline.stop();
					SlideController.this.playPauseButton.setSelected(false);
				} else if (i < model.Utilities.imageFileList.size()) {
					try {
						SlideController.this.image.setImage(new Image(
								model.Utilities.imageFileList.get(i).getImageFile().toURI().toURL().toString(), true));
//						TODO should be selected imageFileList
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					i++;
				} else {
					i = 0;
				}

			}
		};

		KeyFrame keyFrame = new KeyFrame(duration, onFinished);
		timeline.getKeyFrames().add(keyFrame);
	}

	private void addTextFieldEnterEvent() {
		delayTime.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					if (delayTime.getText() != null && !delayTime.getText().isEmpty()) {
						i = currentIndex + 1;
						timeline.stop();
						duration = Duration.seconds(Double.parseDouble(delayTime.getText()));
						newTimeline();
						timeline.play();
					}
				}
			}
		});
	}

	public void showStage() {
		this.newTimeline();
		this.addTextFieldEnterEvent();
		this.slideStage.show();

	}

}
