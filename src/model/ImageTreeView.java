package model;

import java.io.File;
import java.net.MalformedURLException;
import java.text.DecimalFormat;

import controller.MainExplorerController;
import controller.RootController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ImageTreeView{
	
	
	private TreeView<ImageFile> treeView;
	
	private TreeItem<ImageFile> root;
	
	public ImageTreeView(TreeView<ImageFile> treeView) {
		this.treeView = treeView;
		root = new TreeItem<ImageFile>(new ImageFile(""));
		root.setExpanded(true);
		treeView.setRoot(root);
		treeView.setShowRoot(false); 
		
		File[] rootDirectory = File.listRoots();
		
		for(int i = 0; i < rootDirectory.length; i++) {
			ImageTreeItem imageTreeItem = new ImageTreeItem(new ImageFile(rootDirectory[i]));
			root.getChildren().add(imageTreeItem);
		}
		
		loadImage();
	}

	private String getAmountSize(long size)  {
		if(size <= 0) {
			return "0";
		}
		final String[] units = new String[] {"B","KB","MB","GB","TB"};
		int digitGroup = (int)(Math.log10(size)/Math.log10(1024));
		
		return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroup)) + " " + units[digitGroup];
	}
	
	public TreeView<ImageFile> getTreeView(){
		return treeView;
	}
	
	private void loadImage() {
		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ImageFile>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<ImageFile>> observable, TreeItem<ImageFile> oldValue,
					TreeItem<ImageFile> newValue) {
				//单选展开
				if(!newValue.getChildren().isEmpty()) {
					newValue.setExpanded(true);
				}
				//右侧显示
				((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).clearFlowPane();
				long st = System.currentTimeMillis();
				ImageFile currentFile = newValue.getValue();
				if(currentFile.isDirectory()) {
//					long st = System.currentTimeMillis();//test
					ImageFile[] imageFiles = currentFile.listImageFiles(); //1279ms 
//					long et = System.currentTimeMillis();//test
//					System.out.println(et - st);//test
					if(imageFiles != null) {
						int amount = 0;
						long size = 0L;
						for(ImageFile imageFile : imageFiles) {
							amount++;
							size += imageFile.length();
							try {
								ImageLabel imageLabel = new ImageLabel(imageFile);
								((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).getFlowPane().getChildren().add(imageLabel);
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
						}
						model.Utilities.imageFileList.setAll(imageFiles);
						if(amount != 0) {
							((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).setStatusText(amount + "张图片(" + getAmountSize(size) + ")");
							((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).setAmountText("文件夹：" + currentFile.getImageFile().getName() + " - 共"+ amount + "张图片");
						}
					}
				}
				long et = System.currentTimeMillis();
				System.out.println(et - st);
			}
		});
	}
}
