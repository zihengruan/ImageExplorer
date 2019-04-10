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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
	private ImageView image;

	@FXML
	private Button playButton;

	@FXML
	private Button pauseButton;
	
    @FXML
    private Button stopButton;
	
    @FXML
    private Button confirmAndPlayButton;

	@FXML
	private TextField delayTime;

	public void initialize(URL location, ResourceBundle resources) {
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
	void pause(MouseEvent event) {
		timeline.pause();
	}

	@FXML
	void play(MouseEvent event) {
		timeline.play();
	}

    @FXML
    void stop(MouseEvent event) {
    	i = currentIndex;
    	timeline.stop();
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
		Image image = new Image(imageFile.getImageFile().getAbsolutePath(), true);
		this.setImage(image);
	}

	private void newTimeline() {
		ImageFile t_imageFile = ((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImagefile();
		currentIndex = model.Utilities.imageFileList.indexOf(t_imageFile);
		i = currentIndex + 1;
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(i == currentIndex) {
					timeline.stop();
				} else if (i < model.Utilities.imageFileList.size()) {
					try {
						SlideController.this.image.setImage(new Image(
								model.Utilities.imageFileList.get(i).getImageFile().toURI().toURL().toString(), true));
					} catch (MalformedURLException e) {
						e.printStackTrace();

					}
					i++;
				}else {
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
				if(event.getCode().equals(KeyCode.ENTER)) {
					if(delayTime.getText() != null && !delayTime.getText().isEmpty()) {
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
		addTextFieldEnterEvent();
		this.slideStage.show();
		
	}
	
}

