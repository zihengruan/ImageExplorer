package model;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class ImageTreeItem extends TreeItem<ImageFile> {

	private boolean isLeaf;
	private boolean isFirstTimeChildren = true;
	private boolean isFirstTimeLeaf = true;

	public ImageTreeItem(ImageFile imageFile) {
		super(imageFile);
	}

	public ImageTreeItem(File file) {
		setValue(new ImageFile(file));
	}

	@Override
	public boolean isLeaf() {
		if (isFirstTimeLeaf) {
			isFirstTimeLeaf = false;
			ImageFile imageFile = getValue();
			ImageFile[] imageFiles = imageFile.listFiles();
			if (imageFiles == null || imageFiles.length == 0) {
				isLeaf = true;
			} else {
				isLeaf = true;
				for (ImageFile iFile : imageFiles) {
					if (iFile.isDirectory()) {
						isLeaf = false;
					}
				}
			}
		}
		return isLeaf;
	}

	@Override
	public ObservableList<TreeItem<ImageFile>> getChildren() {
		if (isFirstTimeChildren) {
			isFirstTimeChildren = false;
			super.getChildren().setAll(buildChildren(this));
		}
		return super.getChildren();
	}

	private ObservableList<TreeItem<ImageFile>> buildChildren(TreeItem<ImageFile> treeItem) {
		ImageFile imageFile = treeItem.getValue();
		if (imageFile != null && imageFile.isDirectory()) {
			ImageFile[] imageFiles = imageFile.listFiles();
			if (imageFiles != null && imageFiles.length != 0) {
				ObservableList<TreeItem<ImageFile>> children = FXCollections.observableArrayList();

				for (ImageFile childImageFile : imageFiles) {
					if (childImageFile.isHidden() || childImageFile.isFile()) {
						continue;
					}
					children.add(new ImageTreeItem(childImageFile));
				}
				return children;
			}
		}
		return FXCollections.emptyObservableList();
	}
}
