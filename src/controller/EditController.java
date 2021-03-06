package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ImageFile;
import model.ImageLabel;
import model.Utilities;

public class EditController extends RootController {

	private Stage editStage;

	@FXML
	private ImageView imageView;
	
	private ImageFile imageFile;

	@FXML
	private TabPane functionPane;

	@FXML
	private Button rotateButton;

	// 裁剪相关
	private boolean isDrag = false;
	private double rX = 0;
	private double rY = 0;
	private double cutHeight = 0;
	private double cutWidth = 0;
	private double maxHeight;
	private double maxWidth;

	@FXML
	private Rectangle rect;

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
	public void setImage(ImageFile imageFile) {
		Image t_image;
		try {
			t_image = new Image(imageFile.getImageFile().toURI().toURL().toString(), true);
			this.setImageView(t_image);
			this.imageFile = imageFile;
			model.Utilities.resetAll();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	private void calMaxSize() {
		double height = this.getImage().getHeight();
		double width = this.getImage().getWidth();
		if(height/width < 0.75) {
			this.maxWidth = 800;
			this.maxHeight = height/width*800;
		}
		else {
			this.maxHeight = 600;
			this.maxWidth = width/height*600;
		}
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
		this.functionTabName.setText("裁剪");
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
		this.reset();
	}

	void reset() {
		this.imageView.setEffect(null);
		this.cutHeight = 0;
		this.cutWidth = 0;
		this.rect.setWidth(0);
		this.rect.setHeight(0);
		this.ContrastSlider.setValue(0.5);
		this.hueSlider.setValue(0.5);
		this.saturationSlider.setValue(0.5);
		this.brightnessSlider.setValue(0.5);
		this.setHsvValue(0, 0, 0, 0);
	}

	private void addEffectTab() {

		overlay(this.overlayEffectImageview);

		exclusion(this.exclusionEffectImageView);

		sauna(this.saunaEffectImageView);

		rouge(this.rougeEffectImageView);

		sucky(this.suckyEffectImageView);
		
		this.reset();

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
		this.setchsvSlider(0, 0, 0, 0);
		this.setHsvValue(0, 0, 0, 0);
	}

	private void overlay(ImageView imageView) {
		ColorAdjust colorAdjust = new ColorAdjust(0.6, 0, -0.3, 0.25);
		this.setchsvSlider(0.25, 0.6, 0, -0.3);
		imageView.setEffect(colorAdjust);
		this.setHsvValue(0.25, 0.6, 0, -0.3);
	}

	private void exclusion(ImageView imageView) {
		ColorAdjust colorAdjust = new ColorAdjust(-0.3, -0.1, -0.3, -0.25);
		this.setchsvSlider(-0.25, -0.3, -0.1, -0.3 );
		imageView.setEffect(colorAdjust);
		this.setHsvValue(-0.25, -0.3, -0.1, -0.3);
	}

	private void sauna(ImageView imageView) {
		ColorAdjust colorAdjust = new ColorAdjust(-0.1, -0.1, -0.1, -0.2);
		this.setchsvSlider(-0.2, -0.1, -0.1, -0.1) ;
		imageView.setEffect(colorAdjust);
		this.setHsvValue(-0.2, -0.1, -0.1, -0.1);
	}

	private void rouge(ImageView imageView) {
		ColorAdjust colorAdjust = new ColorAdjust(0.15, -0.13, -0.1, -0.17);
		this.setchsvSlider(-0.17, 0.15, -0.13, -0.1 );
		imageView.setEffect(colorAdjust);
		this.setHsvValue(-0.17, 0.15, -0.13, -0.1 );
	}

	private void sucky(ImageView imageView) {
		ColorAdjust colorAdjust = new ColorAdjust(0.30, 0.5, 0.1, 0.1);
		this.setchsvSlider(0.1, 0.30, 0.5, 0.1 );
		imageView.setEffect(colorAdjust);
		this.setHsvValue(0.1, 0.30, 0.5, 0.1);
	}
	
	private void setchsvSlider(double c, double h, double s, double v) {
		this.ContrastSlider.setValue((1+c)/2);
		this.hueSlider.setValue((1+h)/2);
		this.saturationSlider.setValue((1+s)/2);
		this.brightnessSlider.setValue((1+v)/2);
	}

	// 滑动条[0,1] 映射到 colorAdjust[-1,1]
	private void addAdjustTab() {
		this.ContrastSlider.valueProperty()
				.addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
					this.colorAdjust.setContrast(2 * newValue.doubleValue() - 1);
					contrastValue = (2 * newValue.doubleValue() - 1);
					EditController.this.imageView.setEffect(colorAdjust);
				});

		this.hueSlider.valueProperty()
				.addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
					this.colorAdjust.setHue(2 * newValue.doubleValue() - 1);
					hueValue = (2 * newValue.doubleValue() - 1);
					EditController.this.imageView.setEffect(colorAdjust);
				});

		this.saturationSlider.valueProperty()
				.addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
					this.colorAdjust.setSaturation(2 * newValue.doubleValue() - 1);
					saturationValue = (2 * newValue.doubleValue() - 1);
					EditController.this.imageView.setEffect(colorAdjust);
				});

		this.brightnessSlider.valueProperty()
				.addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
					this.colorAdjust.setBrightness(2 * newValue.doubleValue() - 1);
					brightnessValue = (2 * newValue.doubleValue() - 1);
					EditController.this.imageView.setEffect(colorAdjust);
				});
	}

	@FXML
	void saveImage(ActionEvent event) {
		File file = this.imageFile.getImageFile();
		
		Utilities.imageFileList.remove(this.imageFile);
		for(Node imageLabel: ((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).
			getFlowPane().getChildren()) {
			ImageFile imageFile = ((ImageLabel)imageLabel).getImageFile2();
			if(imageFile.equals(this.imageFile)) {
				((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).
					getFlowPane().getChildren().remove(imageLabel);
				break;
			}
		}
		
		try {
			FileInputStream input = new FileInputStream(file);
			BufferedInputStream inBuffer = new BufferedInputStream(input);
			FileOutputStream output = new FileOutputStream("./tmp.jpg");
			BufferedOutputStream outBuffer = new BufferedOutputStream(output);

			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuffer.read(b)) != -1) {
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
		if(this.cutHeight!=0 && this.cutWidth != 0) {
			image = clip(image);
		}
		hsvAddjust(image, this.contrastValue, this.hueValue, this.saturationValue, this.brightnessValue);
		Imgcodecs.imwrite("./tmp.jpg", image);
		
		

		try {
			FileInputStream input = new FileInputStream("./tmp.jpg");
			BufferedInputStream inBuffer = new BufferedInputStream(input);
			FileOutputStream output = new FileOutputStream(file);
			BufferedOutputStream outBuffer = new BufferedOutputStream(output);

			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuffer.read(b)) != -1) {
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

		ImageFile n_image = new ImageFile(file);
		Utilities.imageFileList.add(n_image);
		try {
			((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).
				getFlowPane().getChildren().add(new ImageLabel(n_image));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		this.reset();
	}

	/*
	 * 2019/5/1 更改了保存方式, 使用了opencv 现在能保存和原图一样大的图片了
	 */
	@FXML
	void saveImageCopy(ActionEvent event) {
		File file = this.imageFile.getImageFile();
		try {
			FileInputStream input = new FileInputStream(file);
			BufferedInputStream inBuffer = new BufferedInputStream(input);
			FileOutputStream output = new FileOutputStream("./tmp.jpg");
			BufferedOutputStream outBuffer = new BufferedOutputStream(output);

			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuffer.read(b)) != -1) {
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
		if(this.cutHeight!=0 && this.cutWidth != 0) {
			image = clip(image);
		}
		hsvAddjust(image, this.contrastValue, this.hueValue, this.saturationValue, this.brightnessValue);
		Imgcodecs.imwrite("./tmp.jpg", image);

		String filePath = file.getAbsolutePath();
		String filePathWithOutFormatName = filePath.substring(0, filePath.lastIndexOf("."));
		String imageCopyName = filePathWithOutFormatName + ".(副本).jpg";

		try {
			FileInputStream input = new FileInputStream("./tmp.jpg");
			BufferedInputStream inBuffer = new BufferedInputStream(input);
			FileOutputStream output = new FileOutputStream(imageCopyName);
			BufferedOutputStream outBuffer = new BufferedOutputStream(output);

			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuffer.read(b)) != -1) {
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
		
		ImageFile n_image = new ImageFile(imageCopyName);
		Utilities.imageFileList.add(n_image);
		try {
			((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).
				getFlowPane().getChildren().add(new ImageLabel(n_image));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/*
	 * 2019/5/5
	 * 裁剪
	 */
	 private Mat clip(Mat m) {
		 Rect rect = new Rect();
		 if(this.cutWidth < 0) {
			 rect.x = (int) (this.rX+this.cutWidth);
			 rect.width = (int) -this.cutWidth;
		 }	else {
			 rect.x = (int) this.rX;
			 rect.width = (int) this.cutWidth;
		 }
		 if(this.cutHeight < 0) {
			 rect.y = (int) (this.rY + this.cutHeight);
			 rect.height = (int) -this.cutHeight;
		 }	else {		 
			 rect.y = (int) this.rY;
			 rect.height = (int) this.cutHeight;
		 }
		 double sX = this.getImage().getWidth()/this.maxWidth;
		 double sY = this.getImage().getHeight()/this.maxHeight;
		 rect.x *= sX;
		 rect.width *= sX;
		 rect.y *= sY;
		 rect.height *= sY;
		 return m.submat(rect);
		 
	 }
	
	/*
	 * 2019/5/4 加入调整对比度
	 */
	private void hsvAddjust(Mat m, double dc, double dh, double ds, double dv) {
		int height = m.height();
		int width = m.width();
		double k = Math.tan((45 + 44 * dc) * (Math.PI / 180));
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				double[] hsv = m.get(r, c);
				hsv[0] = (hsv[0] - 127.5) * k + 127.5;
				hsv[1] = (hsv[1] - 127.5) * k + 127.5;
				hsv[2] = (hsv[2] - 127.5) * k + 127.5;
				m.put(r, c, hsv);
			}
		}
		Imgproc.cvtColor(m, m, Imgproc.COLOR_BGR2HSV);
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				double[] hsv = m.get(r, c);
				hsv[0] = hsv[0] + dh * 90;
				if(hsv[0] <0) {
					hsv[0] += 180;
				}else {
					hsv[0] %= 180;
				}
				if (hsv[1] + ds * 255 > 255) {
					hsv[1] = 255;
				} else if (hsv[1] + ds * 255 < 0) {
					hsv[1] = 0;
				} else {
					hsv[1] = hsv[1] + ds * 255;
				}

				if (hsv[2] + dv * 255 > 255) {
					hsv[2] = 255;
				} else if (hsv[2] + dv * 255 < 0) {
					hsv[2] = 0;
				} else {
					hsv[2] = hsv[2] + dv * 255;
				}
				m.put(r, c, hsv);
			}
		}
		Imgproc.cvtColor(m, m, Imgproc.COLOR_HSV2BGR);

	}

	private void addDragAction() {
		this.imageView.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED) && EditController.this.isDrag==false 
						&& EditController.this.functionPane.getSelectionModel().getSelectedIndex()==0) {
					EditController.this.isDrag = true;
					EditController.this.calMaxSize();
					EditController.this.rX = event.getX();
					EditController.this.rY = event.getY();
					EditController.this.rect.setFill(Color.rgb(255, 255, 255, 0.5));
				}
				else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED) && EditController.this.isDrag==true) {
					if(event.getX() >= 0 && event.getX() <= EditController.this.maxWidth) {
						EditController.this.cutWidth = event.getX() - EditController.this.rX;
					}
					
					if(event.getY() >=0 && event.getY() <= EditController.this.maxHeight) {
						EditController.this.cutHeight = event.getY() - EditController.this.rY;
					}
					
					
					if(EditController.this.cutWidth>=0) {
						EditController.this.rect.setX(EditController.this.rX);
						EditController.this.rect.setWidth(EditController.this.cutWidth);
					} else {
						EditController.this.rect.setX(EditController.this.rX + EditController.this.cutWidth);
						EditController.this.rect.setWidth(-EditController.this.cutWidth);
					}
					
					if(EditController.this.cutHeight>=0) {
						EditController.this.rect.setY(EditController.this.rY);
						EditController.this.rect.setHeight(EditController.this.cutHeight);
					} else {
						EditController.this.rect.setY(EditController.this.rY + EditController.this.cutHeight);
						EditController.this.rect.setHeight(-EditController.this.cutHeight);
					}
					
				}
				else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED) && EditController.this.isDrag==true) {
					EditController.this.isDrag = false;
					EditController.this.rect.setFill(Color.rgb(255, 255, 255, 0.2));
				}
				
			}
			
		});
	}
	
	private void setHsvValue(double contrastValue, double hueValue, double saturationValue, double brightnessValue) {
		this.contrastValue = contrastValue;
		this.hueValue = hueValue;
		this.saturationValue = saturationValue;
		this.brightnessValue = brightnessValue;
				
	}
	
	private void addCloseAction() {
		this.editStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				EditController.this.reset();
				EditController.this.selectFuntionPane(0);
			}
		});
	}

	public void showStage() {
		addAdjustTab();
		addEffectTab();
		addDragAction();
		addCloseAction();
		this.rect.setX(0);
		this.rect.setY(0);
		this.rect.setHeight(0);
		this.rect.setWidth(0);
		this.editStage.show();
	}
}
