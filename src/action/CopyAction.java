package action;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import model.ImageLabel;
import model.Utilities;

public class CopyAction {
	public CopyAction() {
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
