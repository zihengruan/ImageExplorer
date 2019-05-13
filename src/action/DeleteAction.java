package action;

import application.Main;
import controller.MainExplorerController;
import controller.RootController;
import model.ImageLabel;
import model.MyAlert;
import model.Utilities;

public class DeleteAction {
	MainExplorerController mainExplorerController;
	public DeleteAction(MainExplorerController mainUI) {
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
		
		if(MyAlert.showAlert("是否删除选中的图片？", "", Main.mainStage)) {
			System.out.println("before delete:");
			System.out.println("selectedImage:"+Utilities.selectedImage);
			System.out.println("selectedImage size:"+Utilities.selectedImage.size());
			System.out.println("selectedfiles:"+Utilities.selectedfiles);
			
			for(ImageLabel iLabel:Utilities.selectedImage) {
				((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).getFlowPane().getChildren().remove(iLabel);
				Utilities.selectedfiles.remove(iLabel.getImageFile());
				iLabel.getImageFile().delete();
			}
			Utilities.selectedImage.clear();
			System.out.println("after delete:");
			System.out.println(Utilities.selectedImage);
			System.out.println(Utilities.selectedfiles);		
//			ImageLabel.getSelectedPictureFiles().clear();
			
		}else {
			ImageLabel.getSelectedPictureFiles().clear();
		}

		for (ImageLabel iLabel : Utilities.selectedImage) {
			iLabel.selected.set(false);
			iLabel.setPress(false);
//			iLabel.setStyle("-fx-background-color:transparent;");
		}
		Utilities.selectedImage.clear();
		Utilities.selectedfiles.clear();
		System.out.println("clean all selected image&files");
	}
}
