package model;

import controller.MainViewerController;
import controller.RootController;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseEventHandler implements EventHandler<MouseEvent> {
	Node node;
	ImageFile imageFile;

	public MouseEventHandler(Node node, ImageFile imageFile) {
		this.node = node;
		this.imageFile = imageFile;
	}

	@Override
	public void handle(MouseEvent event) {
		if (node instanceof ImageLabel) {
			if(event.isControlDown() == false) {//Control没有按下
				if(event.getButton()==MouseButton.PRIMARY) {
					for (ImageLabel iLabel : Utilities.selectedImage) {
						iLabel.selected.set(false);
						iLabel.setPress(false);
					}

					Utilities.selectedImage.removeAll(Utilities.selectedImage);
					Utilities.selectedfiles.removeAll(Utilities.selectedfiles);
					
					Utilities.selectedImageFiles.removeAll(Utilities.selectedImageFiles);//新增选中数组
				}
				((ImageLabel) node).setSelected(true);
				
				if (event.getClickCount() >= 2 && event.getButton() == MouseButton.PRIMARY) {
					// 双击打开事件
					((ImageLabel) node).setSelected(true);
					((MainViewerController) RootController.controllers.get("controller.MainViewerController"))
							.setImage(this.imageFile);
					if (!RootController.controllers.get("controller.MainViewerController").getStage().isShowing()) {
						((MainViewerController) RootController.controllers.get("controller.MainViewerController"))
								.showStage();
						;
					}
				}
				
				
				if (event.getButton() == MouseButton.SECONDARY) {
					new RightClickMenu(((ImageLabel) node), ((ImageLabel) node).mainScene, true);
				}
			}
			if (event.isControlDown() && event.getClickCount() == 1) {// Control按下
				((ImageLabel) node).setSelected(true);
			}
		} else {
			for (ImageLabel iLabel : Utilities.selectedImage) {
				iLabel.selected.set(false);
				iLabel.setPress(false);
			}
			Utilities.selectedImage.clear();
			Utilities.selectedfiles.clear();
			Utilities.selectedImageFiles.clear();//新增选中数组
		}

	}
}
