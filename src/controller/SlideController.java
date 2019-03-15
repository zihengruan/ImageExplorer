package controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.ImageFile;

public class SlideController extends RootController implements CanShowImage{
	
	private Stage slideStage;
	
	@FXML
	private ImageView image;
	
	@FXML
	private Button playStopButton;
	
	@FXML
	private TextField delayTime;
	
	@FXML
	void switchMode(MouseEvent event) {
		//TODO play/stop button
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
				if(newValue == false) {
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



	
	
	   
}
