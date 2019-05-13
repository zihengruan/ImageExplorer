package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import controller.MainExplorerController;
import controller.RootController;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import model.ImageFile;
import model.ImageLabel;
import model.Utilities;

public class PasteAction {
	MainExplorerController mainExplorerController;
	public PasteAction(MainExplorerController mainUI) {
		this.mainExplorerController = mainUI;
		Clipboard clipboard = Clipboard.getSystemClipboard();
		List<File> files = (List<File>) (clipboard.getContent(DataFormat.FILES));
		if (files.size() <= 0) {
			return;
		}
		if (ImageLabel.getCutedPictures().size() > 0) {
			File first = files.get(0);
			
			if(first.getParentFile().getAbsolutePath().compareTo(MainExplorerController.theFilePath) == 0){
				for(ImageLabel pNode : ImageLabel.getCutedPictures()) {
					pNode.getImageView().setEffect(null);
				}

				//清除选中
				for (ImageLabel iLabel : Utilities.selectedImage) {
					iLabel.selected.set(false);
					iLabel.setPress(false);
//					iLabel.setStyle("-fx-background-color:transparent;");
				}
				
				Utilities.selectedImage.clear();;
				Utilities.selectedfiles.clear();;
				System.out.println("clean all selected image&files");
				System.out.println("print selectedImage&files");
				System.out.println(Utilities.selectedImage);
				System.out.println(Utilities.selectedfiles);
				ImageLabel.getCutedPictures().clear();
				clipboard.clear();
				return;	
			}
		}
		
		for(File oldFile : files) {
			String newName = Pasterename(MainExplorerController.theFilePath,oldFile.getName());
			File newFile = new File(MainExplorerController.theFilePath + File.separator+newName);
			
			try {
				newFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(newFile.exists()) {
				try {
					copyFile(oldFile,newFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			ImageFile iFile = new ImageFile(newFile);
			if(!Utilities.imageFileList.contains(iFile)) {
				Utilities.imageFileList.add(new ImageFile(newFile));
				System.out.println("成功添加复制文件！");
				System.out.println("Utilities.imageFileList.add(new ImageFile(newFile));");
			}
			try {
				((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).getFlowPane().getChildren().add(new ImageLabel(new ImageFile(newFile)));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(ImageLabel.getCutedPictures().size()>0) {
				oldFile.delete();	
			}
		}
		clipboard.clear();
	}
	private void copyFile(File fromFile, File toFile) throws IOException {
		FileInputStream inputStream = new FileInputStream(fromFile);
		FileOutputStream outputStream = new FileOutputStream(toFile);
		byte[] b = new byte[1024];
		int byteRead;
		while ((byteRead = inputStream.read(b)) > 0) {
			outputStream.write(b, 0, byteRead);
		}
		inputStream.close();
		outputStream.close();
		
	}
	private String Pasterename(String theFilePath, String name) {
		String newName = name;
		File fatherPathFile = new File(theFilePath);
		File[] filesInFatherPath = fatherPathFile.listFiles();
		for (File fileInFatherPath : filesInFatherPath) {
			String fileName = fileInFatherPath.getName();
			int cmp = newName.compareTo(fileName);
			if (cmp == 0) {
				String str = null;
				int end = newName.lastIndexOf("."), start = newName.lastIndexOf("_副本");
				if (start != -1) {
					str = newName.substring(start, end);
					int num = 1;
					try {
						num = Integer.parseInt(str.substring(str.lastIndexOf("_副本") + 3)) + 1;
						int cnt = 0, d = num - 1;
						while (d != 0) {
							d /= 10;
							cnt++;
						}
						newName = newName.substring(0, end - cnt) + num + newName.substring(end);
					} catch (Exception e) {
						newName = newName.substring(0, end) + "_副本1" + newName.substring(end);
					}

				} else {
					newName = newName.substring(0, end) + "_副本1" + newName.substring(end);
				}
			}
		}
		return newName;
	}
}
