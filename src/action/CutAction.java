package action;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import model.ImageLabel;
import model.Utilities;

public class CutAction {
	public CutAction() {
		if(ImageLabel.getSelectedPictures().size()<=0) {
			return;
		}
		if(ImageLabel.getCutedPictures().size() > 0) {
			for(ImageLabel imageLabel : ImageLabel.getCutedPictures()) {
				imageLabel.getImageView().setEffect(null);
			}
			ImageLabel.getCutedPictures().clear();
		}
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent clipboardContent = new ClipboardContent();
		clipboard.clear();
		for(ImageLabel imageLabel : ImageLabel.getSelectedPictures()) {
//			ImageLabel.getSelectedPictureFiles().add(imageLabel.getImageFile());
			
			imageLabel.getImageView().setEffect(new ColorAdjust(0, 0, 0.5, 0));//标志被剪切
//			ImageLabel.addCutedPictures(imageLabel);
			Utilities.cutedPictures.add(imageLabel);
		}
		
		clipboardContent.putFiles(ImageLabel.getSelectedPictureFiles());
		clipboard.setContent(clipboardContent);
		clipboard = null;
		clipboardContent = null;
	}
}
