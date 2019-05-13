package model;
import java.io.File;
import java.util.ArrayList;

import controller.MainViewerController;
import controller.RootController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Utilities {
	public static ObservableList<ImageFile> imageFileList = FXCollections.observableArrayList(new ArrayList<>());	
	public static ObservableList<ImageLabel> selectedImage = FXCollections.observableArrayList(new ArrayList<>());
	public static ObservableList<ImageFile> selectedImageFiles = FXCollections.observableArrayList(new ArrayList<>());
	public static ObservableList<File> selectedfiles = FXCollections.observableArrayList(new ArrayList<>());
	public static ObservableList<ImageLabel> cutedPictures = FXCollections.observableArrayList(new ArrayList<>()); 
	
	public static double originalFitWidth = 960;
	public static double originalFitHeight = 720;
	private static double zoomRate = 1.25;
	private static double maxWidth = originalFitWidth * Math.pow(zoomRate, 12);
	private static double minWidth = originalFitWidth * Math.pow(zoomRate, -5);
	
	public static void resetAll() {
		((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setRotate(0);
		((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setFitWidth(originalFitWidth);
		((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setFitHeight(originalFitHeight);
		((MainViewerController)RootController.controllers.get("controller.MainViewerController")).resetImagePosition();
	}
	
	public static ImageFile getNextImageFile(ImageFile imageFile) {
		if(imageFileList.indexOf(imageFile)+1 < imageFileList.size()) {
//			return imageFileList.listIterator(imageFileList.indexOf(imageFile)).next();
			return imageFileList.get(imageFileList.indexOf(imageFile)+1);
		}
		else {
			return null;
		}
//		if(imageFileList.listIterator(imageFileList.indexOf(imageFile)).hasNext()) {
////			return imageFileList.listIterator(imageFileList.indexOf(imageFile)).next();
//			return imageFileList.get(imageFileList.indexOf(imageFile)+1);
//		}
//		else {
//			return null;
//		}
	}
	
	public static ImageFile getPriviousImageFile(ImageFile imageFile) {
		if(imageFileList.listIterator(imageFileList.indexOf(imageFile)).hasPrevious()) {
			return imageFileList.listIterator(imageFileList.indexOf(imageFile)).previous();
		}
		else {
			return null;
		}
	}
	
	public static void rotateImage() {
		double rotate = ((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().getRotate();		
		((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setRotate((rotate + 90) % 360);
	}
		
	public static void zoomInImage() {
		double w = ((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().getFitWidth();		
		double h = ((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().getFitHeight();
		if(w < maxWidth) {
			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setFitWidth(w * zoomRate);
			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setFitHeight(h * zoomRate);
		}
	}
	
	public static void zoomOutImage() {
		double w = ((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().getFitWidth();		
		double h = ((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().getFitHeight();	
		if(w > minWidth) {
			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setFitWidth(w / zoomRate);
			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setFitHeight(h / zoomRate);
		}else if (w == minWidth) {
			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).resetImagePosition();
		}
	}
	

}
