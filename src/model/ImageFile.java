package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

public class ImageFile {
	
	private File imageFile;
	
	private String imageName; 
	
	private String imagePath;
	
	private String imageSize;
	
	private int height;
	
	private int widht;
	
	private String imageDate;
	
	

	public ImageFile(String file) {
		this(new File(file));
		
		this.imageName = this.imageFile.getName();
		this.imageSize = getFileSize(this.imageFile.length());
//		this.imagePath = this.imageFile.getAbsolutePath();
		
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy年MM月dd日,E HH:mm");
		
		
		BasicFileAttributeView bView = Files.getFileAttributeView(Paths.get(imageName), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		try {
			BasicFileAttributes bAttributes = bView.readAttributes();
			this.imageDate = dFormat.format(bAttributes.creationTime().toMillis());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public ImageFile(File file) {
		imageFile = file;
		
		this.imageName = this.imageFile.getName();
		this.imageSize = getFileSize(this.imageFile.length());
//		this.imagePath = this.imageFile.getAbsolutePath();
		
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日, E HH:mm");
		this.imageDate = dFormat.format(this.imageFile.lastModified());
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
		if(files == null || files.length == 0) {
			return null;
		}
		
		ImageFile[] imageFiles = new ImageFile[files.length];
		for(int i = 0; i < files.length; i++) {
			imageFiles[i] = new ImageFile(files[i]);
		}
		return imageFiles;
	}
	
	private String getFileSize(long size) {
		if(size <= 0) {
			return "0";
		}
		final String[] units = new String[] {"B","KB","MB","GB","TB"};
		int digitGroup = (int)(Math.log10(size)/Math.log10(1024));
		
		return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroup)) + " " + units[digitGroup];
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

	public String getImageName() {
		return imageName;
	}

	public String getImagePath() {
		return this.imageFile.getParentFile().getAbsolutePath();
	}

	public String getImageSize() {
		return imageSize;
	}

	public String getImageDate() {
		return imageDate ;
	} 
    
	public String getResolution() {
		try {
			BufferedImage reader = ImageIO.read(this.imageFile);
			this.height = reader.getHeight();
			this.widht = reader.getWidth();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return String.format("%d×%d", widht,height);
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
}
