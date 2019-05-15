package application;
	
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import controller.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	public static Window mainStage;
	@Override
	public void start(Stage primaryStage) {
		try {
			initDll();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			//准备好所有窗口
			createStage("/view/MainExplorer.fxml", "controller.MainExplorerController", true);
			createStage("/view/MainViewer.fxml", "controller.MainViewerController", false);
			createStage("/view/Slide.fxml", "controller.SlideController", false);
			createStage("/view/Edit.fxml", "controller.EditController", false);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createStage(String fxmlPath, String controllerName, boolean isShow) {
		try {
			Parent root = FXMLLoader.load(Main.class.getResource(fxmlPath));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("ImageExplorer");
			stage.getIcons().add(new Image("/view/icon.jpg"));
			RootController.controllers.get(controllerName).setStage(stage);
			if(isShow) {
				stage.setMaximized(true);
				stage.show();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void initDll() throws IOException {
		InputStream dllFile = null;
		File fout = null;
		String dllName = null;
		if(System.getProperty("os.arch").endsWith("64")) {
			dllFile = Main.class.getResourceAsStream("/dll/opencv_java410_64.dll");
			System.out.println(Main.class.getResourceAsStream("/dll/opencv_java410_64.dll"));
			fout = new File("./opencv_java410_64.dll");
			dllName = "opencv_java410_64";
		}else {
			dllFile = Main.class.getResourceAsStream("/dll/opencv_java410_86.dll");
			fout = new File("./opencv_java410_86.dll");
			dllName = "opencv_java410_86";
		}
		
		
        BufferedInputStream inBuff = new BufferedInputStream(dllFile);  
        FileOutputStream output = new FileOutputStream(fout);  
        BufferedOutputStream outBuff=new BufferedOutputStream(output);  
          
        byte[] b = new byte[1024 * 5];  
        int len;  
        while ((len =inBuff.read(b)) != -1) {  
            outBuff.write(b, 0, len);  
        }  
        
        outBuff.flush();   
        inBuff.close();  
        outBuff.close();  
        output.close();  
        dllFile.close();
		
		System.loadLibrary(dllName);
    }  
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
