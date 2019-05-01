package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditController extends RootController {

	private Stage editStage;

	@FXML
	private ImageView imageView;

	@FXML
	private TabPane functionPane;

	@FXML
	private Button rotateButton;

	// 滤镜面板
	@FXML
	private Button filterButton;

	@FXML
	private Button originalEffectButton;

	@FXML
	private ImageView originalEffectImageView;

	@FXML
	private Button overlayEffectButton;

	@FXML
	private ImageView overlayEffectImageview;

	@FXML
	private Button exclusionEffectButton;

	@FXML
	private ImageView exclusionEffectImageView;
	
    @FXML
    private Button saunaEffectButton;
    
    @FXML
    private ImageView saunaEffectImageView;
    
    @FXML
    private Button rougeEffectButton;
    
    @FXML
    private ImageView rougeEffectImageView;
    
    @FXML
    private Button suckyEffectButton;
    
    @FXML
    private ImageView suckyEffectImageView;

	// 调整面板
	@FXML
	private Button adjustButton;

	private ColorAdjust colorAdjust = new ColorAdjust();

	@FXML
	private Slider ContrastSlider;

	private double contrastValue = 0.0;

	@FXML
	private Slider hueSlider;

	private double hueValue = 0.0;

	@FXML
	private Slider saturationSlider;

	private double saturationValue = 0.0;

	@FXML
	private Slider brightnessSlider;

	private double brightnessValue = 0.0;

	@FXML
	private Text functionTabName;

	@FXML
	private Button cutAndRotate;

	@FXML
	private Button undoButtton;

	@FXML
	private Button saveCopyButton;

	@FXML
	private Button zoomOutButton;

	@FXML
	private Button saveButton;

	@FXML
	private Button zoomInButton;

	public void setImageView(Image image) {
		this.imageView.setImage(image);
		this.originalEffectImageView.setImage(image);
		this.overlayEffectImageview.setImage(image);
		this.exclusionEffectImageView.setImage(image);
		this.saunaEffectImageView.setImage(image);
		this.rougeEffectImageView.setImage(image);
		this.suckyEffectImageView.setImage(image);
	}

	public Image getImage() {
		return this.imageView.getImage();
	}

	@Override
	public Stage getStage() {
		return this.editStage;
	}

	@Override
	public void setStage(Stage stage) {
		this.editStage = stage;

	}

	private void selectFuntionPane(int index) {
		this.functionPane.getSelectionModel().select(index);
	}

	@FXML
	void shiftToCutPane(ActionEvent event) {
		selectFuntionPane(0);
		this.functionTabName.setText("裁剪与旋转");
	}

	@FXML
	void shiftToFilterPane(ActionEvent event) {
		selectFuntionPane(1);
		this.functionTabName.setText("滤镜");
	}

	@FXML
	void shiftToAdjustPane(ActionEvent event) {
		selectFuntionPane(2);
		this.functionTabName.setText("调整");
	}

	@FXML
	void undoAll(ActionEvent event) {
		this.imageView.setEffect(null);
		this.ContrastSlider.setValue(0.5);
		this.hueSlider.setValue(0.5);
		this.saturationSlider.setValue(0.5);
		this.brightnessSlider.setValue(0.5);
	}

	private void addEffectTab() {

		overlay(this.overlayEffectImageview);

		exclusion(this.exclusionEffectImageView);
		
		sauna(this.saunaEffectImageView);
		
		rouge(this.rougeEffectImageView);
		
		sucky(this.suckyEffectImageView);

		this.originalEffectButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				original(EditController.this.imageView);
			}
		});

		this.overlayEffectButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				overlay(EditController.this.imageView);
			}
		});

		this.exclusionEffectButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				exclusion(EditController.this.imageView);
			}
		});
		
		this.saunaEffectButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				sauna(EditController.this.imageView);
			}
		});
		
		this.rougeEffectButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				rouge(EditController.this.imageView);
			}
		});
		
		this.suckyEffectButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				sucky(EditController.this.imageView);
			}
		});

	}

	private void original(ImageView imageView) {
		imageView.setEffect(null);
	}

	private void overlay(ImageView imageView) {
		Blend blend = new Blend();
		blend.setMode(BlendMode.OVERLAY);
		imageView.setEffect(blend);
	}

	private void exclusion(ImageView imageView) {
		Blend blend = new Blend();
		blend.setMode(BlendMode.EXCLUSION);
		imageView.setEffect(blend);
	}
	
	private void sauna(ImageView imageView) {
		ColorAdjust colorAdjust = new ColorAdjust(-0.1, -0.1, -0.1, -0.2);
		imageView.setEffect(colorAdjust);
	}
	
	private void rouge(ImageView imageView) {
		ColorAdjust colorAdjust = new ColorAdjust(0.15, -0.13, -0.1, -0.17);
		imageView.setEffect(colorAdjust);
	}
	
	private void sucky(ImageView imageView) {
		ColorAdjust colorAdjust = new ColorAdjust(0.30, 0.5, 0.1, 0.1);
		imageView.setEffect(colorAdjust);
	}

	// 滑动条[0,1] 映射到 colorAdjust[-1,1]
	private void addAdjustTab() {
		this.ContrastSlider.valueProperty()
				.addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
					this.colorAdjust.setContrast(2*newValue.doubleValue()-1);
					contrastValue = (2*newValue.doubleValue()-1);
					EditController.this.imageView.setEffect(colorAdjust);
					System.out.println("contast:" + contrastValue);
				});

		this.hueSlider.valueProperty()
				.addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
					this.colorAdjust.setHue(2*newValue.doubleValue()-1);
					hueValue = (2*newValue.doubleValue()-1);
					EditController.this.imageView.setEffect(colorAdjust);
					System.out.println("hue:" + hueValue);
				});

		this.saturationSlider.valueProperty()
				.addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
					this.colorAdjust.setSaturation(2*newValue.doubleValue()-1);
					saturationValue = (2*newValue.doubleValue()-1);
					EditController.this.imageView.setEffect(colorAdjust);
					System.out.println("saturation:" + saturationValue);
				});

		this.brightnessSlider.valueProperty()
				.addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
					this.colorAdjust.setBrightness(2*newValue.doubleValue()-1);
					brightnessValue = (2*newValue.doubleValue()-1);
					EditController.this.imageView.setEffect(colorAdjust);
					System.out.println("brightness:" + brightnessValue );
				});
	}

	@FXML
	void saveImage(ActionEvent event) {
//		TODO should be selectedImage
		File file = ((MainViewerController) RootController.controllers.get("controller.MainViewerController"))
				.getImagefile().getImageFile();

		WritableImage image = this.imageView.snapshot(new SnapshotParameters(), null);

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 *  2019/5/1
	 *  更改了保存方式, 使用了opencv
	 *  现在能保存和原图一样大的图片了
	 *  TODO 对比度  更准确的映射
	 */
	@FXML
	void saveImageCopy(ActionEvent event) {
//		TODO should be selectedImage
		File file = ((MainViewerController) RootController.controllers.get("controller.MainViewerController"))
				.getImagefile().getImageFile();
		try {
			FileInputStream input = new FileInputStream(file);
			BufferedInputStream inBuffer = new BufferedInputStream(input);
			FileOutputStream output = new FileOutputStream("./tmp.jpg");
			BufferedOutputStream outBuffer = new BufferedOutputStream(output);
			
			byte[] b = new byte[1024*5];
			int len;
			while((len = inBuffer.read(b)) != -1) {
				outBuffer.write(b, 0, len);
			}
			outBuffer.flush();
			inBuffer.close();
			outBuffer.close();
			output.close();
			input.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Mat image = Imgcodecs.imread("./tmp.jpg");
		Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2HSV);
		hsvAddjust(image, this.hueValue, this.saturationValue, this.brightnessValue);
		Imgproc.cvtColor(image, image, Imgproc.COLOR_HSV2BGR);
		Imgcodecs.imwrite("./tmp.jpg", image);
		
		String filePath = file.getAbsolutePath();
		String filePathWithOutFormatName = filePath.substring(0, filePath.lastIndexOf("."));
		String imageCopyName = filePathWithOutFormatName + ".(副本).jpg";
		
		try {
			FileInputStream input = new FileInputStream("./tmp.jpg");
			BufferedInputStream inBuffer = new BufferedInputStream(input);
			FileOutputStream output = new FileOutputStream(imageCopyName);
			BufferedOutputStream outBuffer = new BufferedOutputStream(output);
			
			byte[] b = new byte[1024*5];
			int len;
			while((len = inBuffer.read(b)) != -1) {
				outBuffer.write(b, 0, len);
			}
			outBuffer.flush();
			inBuffer.close();
			outBuffer.close();
			output.close();
			input.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		WritableImage image = this.imageView.snapshot(new SnapshotParameters(), null);
//		
//		String filePath = file.getAbsolutePath();
//		String filePathWithOutFormatName = filePath.substring(0, filePath.lastIndexOf("."));
//		String imageCopyName =filePathWithOutFormatName + ".(副本).jpg";
//		
//		File imageCopy = new File(imageCopyName);
//		
//		try {
//			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageCopy);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	private void hsvAddjust(Mat m, double dh, double ds, double dv) {
		int height = m.height();
		int width = m.width();
		for (int r = 0; r<height; r++) {
			for(int c = 0; c<width; c++) {
				double[] hsv = m.get(r, c);
				hsv[0] = hsv[0]+dh*90;
				hsv[0] %= 180;
				if(hsv[1]+ds*255 > 255) {
					hsv[1] = 255;
				}
				else if(hsv[1]+ds*255 < 0) {
					hsv[1] = 0;
				}
				else {
					hsv[1] = hsv[1]+ds*255;
				}
				
				if(hsv[2]+dv*255 > 255) {
					hsv[2] = 255;
				}
				else if(hsv[2]+dv*255 < 0) {
					hsv[2] = 0;
				}
				else {
					hsv[2] = hsv[2]+dv*255;
				}
				m.put(r, c, hsv);
			}
		}
	}
	
	

	public void showStage() {
		addAdjustTab();
		addEffectTab();
		this.editStage.show();
	}
}
