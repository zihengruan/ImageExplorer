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
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 1) { 
					
					((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).setNameText("名称：\n\n" + ImageLabel.this.imageFile.getImageName());
					((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).setDateText("创建时间：\n\n" + ImageLabel.this.imageFile.getImageDate());
					((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).setSizeText("大小：\n\n" + ImageLabel.this.imageFile.getImageSize());
					((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).setPathText("文件夹路径：\n\n" + ImageLabel.this.imageFile.getImagePath());
					((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).setResText("分辨率：\n\n" + ImageLabel.this.imageFile.getResolution());
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
