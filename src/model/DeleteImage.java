package model;

import application.Main;
import controller.MainExplorerController;
import controller.MainViewerController;
import controller.RootController;

public class DeleteImage {
	MainExplorerController mainExplorerController;
	public DeleteImage(MainExplorerController mainUI) {
		mainExplorerController = mainUI;

		if(ImageLabel.getSelectedPictures().size()<=0) {
			return;
		}
		if(ImageLabel.getCutedPictures().size() > 0) {
			for(ImageLabel pNode : ImageLabel.getCutedPictures()) {
				pNode.getImageView().setEffect(null);
			}
			ImageLabel.getCutedPictures().clear();
		}
		
		if(AlertBox.showAlert("是否删除选中的图片？", "", Main.mainStage)) {
			for(ImageLabel iLabel:Utilities.selectedImage) {
				((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).getFlowPane().getChildren().remove(iLabel);
				if(((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getImagefile().equals(iLabel.getImageFile2())) {
					if(((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getStage().isShowing()) {
						((MainViewerController)RootController.controllers.get("controller.MainViewerController")).getStage().close();
					}
				}
				Utilities.selectedfiles.remove(iLabel.getImageFile());
				Utilities.imageFileList.remove(iLabel.getImageFile2());
				iLabel.getImageFile().delete();
			}
			Utilities.selectedImage.clear();
			
		}else {
			ImageLabel.getSelectedPictureFiles().clear();
		}

		for (ImageLabel iLabel : Utilities.selectedImage) {
			iLabel.selected.set(false);
			iLabel.setPress(false);
		}
		Utilities.selectedImage.clear();
		Utilities.selectedfiles.clear();
	}
}
