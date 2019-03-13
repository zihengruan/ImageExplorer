package model;

import java.net.MalformedURLException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ImageLabel extends Label {
	

	private ImageFile imageFile;
	
	public BooleanProperty selected = new SimpleBooleanProperty();


	public ImageLabel(ImageFile imageFile) throws MalformedURLException {
		this.imageFile = imageFile;
		
		Image image = new Image(imageFile.getImageFile().toURI().toURL().toString(), 150, 150, true, false, true);
		ImageView imageView = new ImageView(image);
		setGraphic(imageView);
		setText(imageFile.getImageFile().getName());
		
		setGraphicTextGap(20);
		setContentDisplay(ContentDisplay.TOP);
		setPrefSize(170, 170);
		setPadding(new Insets(2, 2, 2, 2));
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2) {
					//TODO to viewer
				}
				
			}
		});
	}

	public ImageFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(ImageFile imageFile) {
		this.imageFile = imageFile;
	}

	public void setSelected(boolean selected) {
		this.selected.set(selected);
	}
	
	
	
}
