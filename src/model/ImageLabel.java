package model;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

import controller.MainExplorerController;
import controller.MainViewerController;
import controller.RootController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;

public class ImageLabel extends Label {
	
	private ImageFile imageFile;
	
	private Image image;
	
	private ImageView imageView;
	
	private ImageLabel imageLabel = this;
	
	public MainExplorerController mainScene;
	
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
		super.setAlignment(Pos.CENTER);
		super.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new MouseEventHandler(this,imageFile));
		

	}
	
	
	public void setSelected(boolean value) {
		boolean istrue = selected.get();
		selected.set(value);
		if (selected.get() && !istrue) {
			Utilities.selectedImage.add(this);
			Utilities.selectedfiles.add(this.getImageFile());
			Utilities.selectedImageFiles.add(this.imageFile);//新增选中数组 imagefile
			imageLabel.setStyle("-fx-background-color:#a7a7a7;");
		}
		else if (istrue && !selected.get()) {
			Utilities.selectedImage.remove(this);
			Utilities.selectedfiles.remove(this.getImageFile());
			imageLabel.setStyle("-fx-background-color:transparent;");
		}
		System.out.println(Utilities.selectedImage.size());
		System.out.println(Utilities.selectedfiles.size());
//		mainScene.getTextTwo().setText("已选中 "+selectedPictures.size()+" 张图片");
	}
	

	public File getImageFile() {
//		return this.imageFile;
		return this.imageFile.getImageFile();
	}
	
	public ImageFile getImageFile2() {
		return this.imageFile;
//		return this.imageFile.getImageFile();
	}
	
	public void setImageFile(ImageFile imageFile) {
		this.imageFile = imageFile;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public Image getImage() {
		return image;
	}

	public static ObservableList<ImageLabel> getSelectedPictures() {
		return Utilities.selectedImage;
	}
	
	public static ObservableList<File> getSelectedPictureFiles() {
		return Utilities.selectedfiles;
	}
	
	public static ObservableList<ImageLabel> getCutedPictures() {
		return Utilities.cutedPictures;
	}
}
