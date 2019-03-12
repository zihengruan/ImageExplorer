package model;

import java.net.MalformedURLException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageLabel extends Label {

	private ImageFile imageFile;
	
	public BooleanProperty selected = new SimpleBooleanProperty();
	
	public ImageLabel() {
	}

	public ImageLabel(String text) {
		super(text);
	}

	public ImageLabel(String text, Node graphic) {
		super(text, graphic);
	}

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
