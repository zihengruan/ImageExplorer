package model;

import java.net.MalformedURLException;

import controller.MainExplorerController;
import controller.MainViewerController;
import controller.RootController;
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
	
	private Image image;
	
	private ImageView imageView;
	
	public BooleanProperty selected = new SimpleBooleanProperty();


	/**
	 * @param imageFile
	 * @throws MalformedURLException
	 */
	public ImageLabel(ImageFile imageFile) throws MalformedURLException {
		this.imageFile = imageFile;
		this.image = new Image(imageFile.getImageFile().toURI().toURL().toString(), 150, 150, true, false, true);
		this.imageView = new ImageView(this.image);
		super.setGraphic(this.imageView);
		super.setText(this.imageFile.getImageFile().getName());
		super.setGraphicTextGap(20);
		super.setContentDisplay(ContentDisplay.TOP);
		super.setPrefSize(170, 170);
		super.setPadding(new Insets(2, 2, 2, 2));
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2) {
					((MainViewerController)RootController.controllers.get("controller.MainViewerController")).setImage(ImageLabel.this.imageFile);
					if(!RootController.controllers.get("controller.MainViewerController").getStage().isShowing()) {
						RootController.controllers.get("controller.MainViewerController").getStage().show();
					}
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

	public ImageView getImageView() {
		return imageView;
	}

	public Image getImage() {
		return image;
	}
	
	
	
}
