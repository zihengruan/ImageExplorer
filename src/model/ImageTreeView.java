package model;

import java.io.File;
import java.net.MalformedURLException;

import controller.MainExplorerController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ImageTreeView{
	
	private MainExplorerController mainExplorerController;
	
	private TreeView<ImageFile> treeView;
	
	private TreeItem<ImageFile> root;
	
	public ImageTreeView(MainExplorerController mainExplorerController, TreeView<ImageFile> treeView) {
		this.mainExplorerController = mainExplorerController;
		this.treeView = treeView;
		root = new TreeItem<ImageFile>(new ImageFile("root"));
		root.setExpanded(true);
		treeView.setRoot(root);
		
		File[] rootDirectory = File.listRoots();
		
		for(int i = 0; i < rootDirectory.length; i++) {
			ImageTreeItem imageTreeItem = new ImageTreeItem(new ImageFile(rootDirectory[i]));
			root.getChildren().add(imageTreeItem);
		}
		
		loadImage();
	}

	private void loadImage() {
		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ImageFile>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<ImageFile>> observable, TreeItem<ImageFile> oldValue,
					TreeItem<ImageFile> newValue) {
				mainExplorerController.clearFlowPane();
				ImageFile currentFile = treeView.getSelectionModel().getSelectedItem().getValue();
				if(currentFile.isDirectory()) {
					ImageFile[] imageFiles = currentFile.listFiles();
					for(ImageFile imageFile : imageFiles) {
						if(imageFile.isImageFile()) {
							try {
								ImageLabel imageLabel = new ImageLabel(imageFile);
								mainExplorerController.getFlowPane().getChildren().add(imageLabel);			
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
						}
					}
					
				}
			}
		});
	}
	
	public TreeView<ImageFile> getTreeView(){
		return treeView;
	}
}
