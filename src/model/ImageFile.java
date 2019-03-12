package model;

import java.io.File;

public class ImageFile {
	
	private File imageFile;

	public ImageFile(String file) {
		this(new File(file));
		
	}
	
	public ImageFile(File file) {
		imageFile = file;
	}
	
	public boolean isImageFile() {
		if(imageFile.getPath().toLowerCase().endsWith(".jpg")||
				imageFile.getPath().toLowerCase().endsWith(".jpeg")||
				imageFile.getPath().toLowerCase().endsWith(".gif")||
				imageFile.getPath().toLowerCase().endsWith(".png")||
				imageFile.getPath().toLowerCase().endsWith(".bmp")) {
			return true;
		} else {
			return false;
		}
	}

	public ImageFile[] listFiles() {
		File[] files = imageFile.listFiles();
		if(files == null || files.length == 0) {//先判空，否则会NullPointException
			return null;
		}
		
		ImageFile[] imageFiles = new ImageFile[files.length];
		for(int i = 0; i < files.length; i++) {
			imageFiles[i] = new ImageFile(files[i]);
		}
		return imageFiles;
	}
	
    public boolean isDirectory() {
    	return imageFile.isDirectory();
    }
    
    public boolean isFile() {
    	return imageFile.isFile();
    }
    
    public boolean isHidden() {
    	return imageFile.isHidden();
    }
    
    public long length() {
    	return imageFile.length();
    }
    
    //toString使文件夹在TreeView显示正确文件名
    //否则会以默认toString方式显示
    public String toString() {
    	if(imageFile.getName().equals("")) {
    		return imageFile.getPath();
    	} else {
    		return imageFile.getName();
    	}
	}
    
    public File getImageFile() {
		return imageFile;
	}   
}
