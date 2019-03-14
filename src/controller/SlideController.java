package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SlideController extends RootController {
	
	private Stage slideStage;
	
	@FXML
	private ImageView image;
	
	@FXML
	private Button playStopButton;
	
	@FXML
	private TextField delayTime;
	
	@FXML
	void switchMode(ActionEvent event) {
		//TODO play/stop button
	}
	
	@Override
	public Stage getStage() {
		return this.slideStage;
	}
	
	@Override
	public void setStage(Stage slideStage) {
		this.slideStage = slideStage;
	}

	
	
	   
}
