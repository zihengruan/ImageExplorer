package model;

import java.util.ArrayList;

import controller.MainViewerController;
import controller.RootController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Utilities {
	public static ObservableList<ImageFile> selectedImage = FXCollections.observableArrayList(new ArrayList<>());
	public static ObservableList<ImageFile> imageFileList = FXCollections.observableArrayList(new ArrayList<>());	
	public static double originalFitWidth = 960;
	public static double originalFitHeight = 720;
	private static double zoomRate = 1.25;
	
	public static void resetAll() {
		((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setRotate(0);
		((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setFitWidth(originalFitWidth);
		((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setFitHeight(originalFitHeight); 
	}
	
	public static ImageFile getNextImageFile(ImageFile imageFile) {
		if(imageFileList.listIterator(imageFileList.indexOf(imageFile)).hasNext()) {
//			return imageFileList.listIterator(imageFileList.indexOf(imageFile)).next();
			return imageFileList.get(imageFileList.indexOf(imageFile)+1);
		}
		else {
			return null;
		}
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
		if(w < 3662) {
			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setFitWidth(w * zoomRate);
			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setFitHeight(h * zoomRate);
		}
	}
	
	public static void zoomOutImage() {
		double w = ((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().getFitWidth();		
		double h = ((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().getFitHeight();	
		if(w > 252) {
			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setFitWidth(w / zoomRate);
			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImageView().setFitHeight(h / zoomRate);
		}
	}
	

}
