package model;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

public class ImageFile {

	private File imageFile;

	private String imageName;

	private String imageSize;

	private int height;

	private int width;

	private String imageLastModifiedDate;

	public ImageFile(File file) {
		imageFile = file;

		this.imageName = this.imageFile.getName();
		this.imageSize = getFileSize(this.imageFile.length());

		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日, E HH:mm");

		this.imageLastModifiedDate = dFormat.format(this.imageFile.lastModified());
	}

	public ImageFile(String file) {
		this(new File(file));

		this.imageName = this.imageFile.getName();
		this.imageSize = getFileSize(this.imageFile.length());

		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy年MM月dd日,E HH:mm");

		this.imageLastModifiedDate = dFormat.format(this.imageFile.lastModified());
	}

	private String getFileSize(long size) {
		if (size <= 0) {
			return "0";
		}
		final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
		int digitGroup = (int) (Math.log10(size) / Math.log10(1024));

		return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroup)) + " " + units[digitGroup];
	}

	public int getHeight() {
		return height;
	}

	public String getImageLastModifiedDate() {
		return imageLastModifiedDate;
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

	public String getResolution() {
//		try {
//			BufferedImage reader = ImageIO.read(this.imageFile);
//			this.height = reader.getHeight();
//			this.width = reader.getWidth();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		Iterator<ImageReader> iter = ImageIO.getImageReadersByFormatName(this.imageFile.getName().substring(this.imageFile.getName().lastIndexOf(".") + 1));
		ImageReader reader = null;
		int width = 0, height = 0;
		try {
			ImageInputStream stream = new FileImageInputStream(new File(this.imageFile.getAbsolutePath()));
			reader = iter.next();
			reader.setInput(stream);
			width = reader.getWidth(reader.getMinIndex());
			height = reader.getHeight(reader.getMinIndex());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader.dispose();
		}
		return String.format("%d×%d", width, height);
	}

	public int getWidth() {
		return width;
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

	/*
	 * 弃用函数
	 */
	public boolean isImageFile() {
		if (imageFile.getPath().toLowerCase().endsWith(".jpg") || imageFile.getPath().toLowerCase().endsWith(".jpeg")
				|| imageFile.getPath().toLowerCase().endsWith(".gif")
				|| imageFile.getPath().toLowerCase().endsWith(".png")
				|| imageFile.getPath().toLowerCase().endsWith(".bmp")) {
			return true;
		} else {
			return false;
		}
	}

	public long length() {
		return imageFile.length();
	}

	// 获取文件列表
	public ImageFile[] listFiles() {
		File[] files = imageFile.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		ImageFile[] imageFiles = new ImageFile[files.length];
		for (int i = 0; i < files.length; i++) {
			imageFiles[i] = new ImageFile(files[i]);
		}
		return imageFiles;
	}

	// 获取图片文件
	public ImageFile[] listImageFiles() {

		// 内部类 用于过滤文件
		class ImageFileFilter implements FilenameFilter {
			private Pattern pattern = null;

			public ImageFileFilter() {
				this.pattern = Pattern
						.compile(".+(\\.JPEG|\\.jpeg|\\.JPG|\\.jpg|\\.png|\\.PNG|\\.gif|\\.GIF|\\.bmp|\\.BMP)");
			}

			@Override
			public boolean accept(File dir, String name) {
				return this.pattern.matcher(name).matches();
			}
		}

		ImageFileFilter iff = new ImageFileFilter();
		File[] files = this.imageFile.listFiles(iff);
		ImageFile[] imageFiles = new ImageFile[files.length];
		for (int i = 0; i < files.length; i++) {
			imageFiles[i] = new ImageFile(files[i]);
		}
		return imageFiles;

	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String toString() {
		if (imageFile.getName().equals("")) {
			return imageFile.getPath();
		} else {
			return imageFile.getName();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof ImageFile))
			return false;
		return ((ImageFile) obj).getImageFile().equals(this.imageFile);
	}
}
