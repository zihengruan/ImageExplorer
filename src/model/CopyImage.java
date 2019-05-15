package model;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class CopyImage {
	public CopyImage() {
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

		
		clipboardContent.putFiles(Utilities.selectedfiles);
		clipboard.setContent(clipboardContent);
		clipboard = null;
		clipboardContent = null;
	}
}
