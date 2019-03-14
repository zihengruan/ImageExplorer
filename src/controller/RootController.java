package controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

public abstract class RootController implements Initializable, ControllerInterface{
	
	public static HashMap<String, RootController> controllers = new HashMap<>();
	
	
	public RootController() {
		String name = this.getClass().getName();
		RootController.controllers.put(name, this);
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}




}
