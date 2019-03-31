package model;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Utilities {
	public static ObservableList<ImageFile> selectedImage = FXCollections.observableList(new ArrayList<>());
	public static ObservableList<ImageFile> imageList = FXCollections.observableArrayList(new ArrayList<>());
	
}
