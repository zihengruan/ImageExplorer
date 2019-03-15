package controller;

import javafx.scene.image.Image;
import model.ImageFile;

public interface CanShowImage {
	public void setImage(Image image);
	public void setImage(ImageFile imageFile);
}
