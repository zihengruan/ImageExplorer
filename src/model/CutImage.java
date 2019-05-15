package model;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class CutImage {
	public CutImage() {
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
			imageLabel.getImageView().setEffect(new ColorAdjust(0, 0, 0.5, 0));//标志被剪切
			Utilities.cutedPictures.add(imageLabel);
		}
		
		clipboardContent.putFiles(ImageLabel.getSelectedPictureFiles());
		clipboard.setContent(clipboardContent);
		clipboard = null;
		clipboardContent = null;
	}
}
