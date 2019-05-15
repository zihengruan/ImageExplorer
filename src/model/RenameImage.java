package model;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import controller.MainExplorerController;
import controller.RootController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RenameImage {
	private MainExplorerController mainExplorerController;
	private Stage anotherStage;
	private Label msg;
	private Button submit;
	private GridPane grid;
	final private TextField name = new TextField();
	final private TextField startNum = new TextField();
	final private TextField bitNum = new TextField();
	private boolean single;
	
	public  RenameImage(MainExplorerController mainUI) {
		this.mainExplorerController = mainUI;
		single = true;
		if(Utilities.selectedImage.size()==1) {
			 single = true;
		}else {
			 single = false;
		}
		anotherStage = new Stage();
		grid = new GridPane();            
		msg = new Label();
		submit = new Button("确认");
		setStage();
	}
	private void setStage() {
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		Label label1 = new Label("名称");
		GridPane.setConstraints(label1, 0, 0);
		name.setPromptText("请输入新名字");
		name.setPrefColumnCount(20);
		name.getText();
		GridPane.setConstraints(name, 1, 0);
		grid.getChildren().addAll(label1,name);
		
		if(single) {	
			GridPane.setConstraints(msg, 0, 1);
			GridPane.setConstraints(submit, 0, 2);
			grid.getChildren().addAll(submit,msg);
		}else {
			Label label2 = new Label("起始编号");
			GridPane.setConstraints(label2, 0, 1);
			startNum.setPromptText("请输入起始编号");
			startNum.setPrefColumnCount(15);
			startNum.getText();
			GridPane.setConstraints(startNum, 1, 1);
			Label label3 = new Label("编号位数");
			GridPane.setConstraints(label3, 0, 2);
			bitNum.setPromptText("请输入编号位数");
			bitNum.setPrefColumnCount(10);
			bitNum.getText();
			GridPane.setConstraints(bitNum, 1, 2);
			GridPane.setConstraints(msg, 1, 3);
			GridPane.setConstraints(submit, 0, 4);
			grid.getChildren().addAll(label2,startNum,label3,bitNum,submit,msg);
		}
		
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			    public void handle(ActionEvent e) {
					if(single) {
						if ((name.getText() != null && !name.getText().isEmpty())) {
				        	if(renameSingle()) {
				        		anotherStage.close();
				        	}else{
				        		msg.setText("该名字的图片已存在，请重新输入!");
				        	}
				        } else {
				            msg.setText("检测不到输入，请输入!");
				        }
					}else {
						if ((name.getText() != null && !name.getText().isEmpty())
							&&(startNum.getText() != null && !startNum.getText().isEmpty())
							&&(bitNum.getText() != null && !bitNum.getText().isEmpty())) {
				        	if(renameMore()) {
				        		anotherStage.close();
				        	}else {
				        		msg.setText("Error! Try again with other parameters.");
				        	}
				        } else {
				            msg.setText("You have not completed the infomations.");
				        }
					}
			     }
			 });
		 Scene scene=new Scene(grid);
		 anotherStage.setTitle("重命名");
		 anotherStage.setScene(scene);
		 anotherStage.show();
	}
	
	private boolean renameSingle() {
		ImageLabel pNode = Utilities.selectedImage.get(0);
		File file = pNode.getImageFile();
		String pre = file.getParent();
    	String[] strings = file.getName().split("\\.");
    	String suf = strings[strings.length-1];
    	File tmp = new File(pre+"\\"+name.getText()+"."+suf);
    	if(!file.renameTo(tmp)) {
			tmp.delete();
			return false;
		}
		pNode.setSelected(false);

		ImageLabel nNode;
		try {
			nNode = new ImageLabel(new ImageFile(tmp));
			((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).getFlowPane().getChildren().remove(pNode);
			((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).getFlowPane().getChildren().add(nNode);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	private boolean renameMore() {
          
		File file;
		
		int id = Integer.valueOf(startNum.getText());
		int bit = Integer.valueOf(bitNum.getText());
		if( id<0 || (id + ImageLabel.getSelectedPictures().size()) >= (int)Math.pow(10, bit)) 
			return false;
		ArrayList<ImageLabel> oldList = new ArrayList<>();
		ArrayList<ImageLabel> newList = new ArrayList<>();
		for (ImageLabel image : ImageLabel.getSelectedPictures()) {
			file = image.getImageFile();
			String pre = file.getParent();
	    	String[] strings = file.getName().split("\\.");
	    	String suf = strings[strings.length-1];
	    	String newName = createName(id,bit);
	    
	    	File tmp = new File(pre+"\\"+newName+"."+suf);
	    	if(!file.renameTo(tmp)){//可能存在失败的情况,如名字重复
				tmp.delete();
				return false;
			}
			oldList.add(image);	
			ImageLabel newImage;
			try {
				newImage = new ImageLabel(new ImageFile(tmp));
				newList.add(newImage);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    	id++;
		}
		for(int i=0;i<oldList.size();i++) {
			oldList.get(i).setSelected(false);
			((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).getFlowPane().getChildren().remove(oldList.get(i));
			Utilities.imageFileList.remove(oldList.get(i).getImageFile2());
			newList.get(i).setSelected(true);
			((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).getFlowPane().getChildren().add(newList.get(i));
			Utilities.imageFileList.add(newList.get(i).getImageFile2());
		}
		return true;
	}
	private String createName(int id,int bit) {
		String newName = name.getText();
		
		int tt = id;
    	int cnt=0;
    	while(tt!=0) {
    		cnt++;
    		tt/=10;
    	}
    	if(id==0)  cnt++;
		while(bit>cnt) {
			newName+=0;
			cnt++;
		}
    	newName += id;
		return newName;
	}
}
