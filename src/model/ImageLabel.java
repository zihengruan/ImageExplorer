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
import javafx.scene.text.Text;

public class ImageLabel extends Label {
	
	//TODO
	

	private ImageFile imageFile;
	
	public BooleanProperty selected = new SimpleBooleanProperty();


	/**
	 * @param imageFile
	 * @throws MalformedURLException
	 */
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
	
	
	
}
