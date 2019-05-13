package model;

import controller.MainExplorerController;
import controller.RootController;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PanelListener {
	Node node;
	MainExplorerController mainExplorerController;
	private Rectangle selectRectangle;
	double nowX;
	double nowY;
	double width;
	double height;
	private boolean isDragged;
	
	public PanelListener(Node node,MainExplorerController mainExplorerController, Rectangle rect) {
		this.node = node;
		this.mainExplorerController = mainExplorerController;
		selectRectangle = rect;
		selectRectangle.setStroke(Color.DARKGRAY);
		selectRectangle.setStrokeWidth(1);
		selectRectangle.setFill(Color.rgb(72, 214, 255, 0.5));
		addListener();
	}
	private void addListener() {
		//鼠标按下，初始化选择矩阵的左上角点
		node.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
			isDragged = false;
			this.nowX = e.getX();
			this.nowY = e.getY();
			this.width = 0;
			this.height = 0;
			selectRectangle.setX(this.nowX);
			selectRectangle.setY(this.nowY);
			selectRectangle.setHeight(0);
			selectRectangle.setWidth(0);
			if(e.getButton()==MouseButton.PRIMARY) {
				for (ImageLabel iLabel : Utilities.selectedImage) {
					iLabel.selected.set(false);
					iLabel.setPress(false);
//					iLabel.setStyle("-fx-background-color:transparent;");
				}

			}
		
		});
		
		node.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent e) -> {
			System.out.println("" + e.getX() + "," + e.getY() );
			if(this.isDragged = true) {
				if(e.getX() >= 0) {
					this.width = e.getX() - this.nowX;
				}
				
				if(e.getY() >=0) {
					this.height = e.getY() - this.nowY;
				}
				
				
				if(this.width>=0) {
					this.selectRectangle.setX(this.nowX);
					this.selectRectangle.setWidth(this.width);
				} else {
					this.selectRectangle.setX(this.nowX + this.width);
					this.selectRectangle.setWidth(-this.width);
				}
				
				if(this.height>=0) {
					this.selectRectangle.setY(this.nowY);
					this.selectRectangle.setHeight(this.height);
				} else {
					this.selectRectangle.setY(this.nowY + this.height);
					this.selectRectangle.setHeight(-this.height);
				}

			}
			
			if(this.isDragged) {		
				
//				ImageLabel.clearSelected();
				for (ImageLabel iLabel : Utilities.selectedImage) {
					iLabel.selected.set(false);
					iLabel.setPress(false);
//					iLabel.setStyle("-fx-background-color:transparent;");
				}
				
				Utilities.selectedImage.removeAll(Utilities.selectedImage);
				Utilities.selectedfiles.removeAll(Utilities.selectedfiles);
				System.out.println("clean all selected image&files");
				System.out.println("print imageFIleList:");
				System.out.println(Utilities.selectedfiles);
				for(Node childrenNode:  ((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).getFlowPane().getChildren()) {
					if(childrenNode instanceof ImageLabel) {
						if(isRectOverlap((ImageLabel)childrenNode))
							((ImageLabel)childrenNode).setSelected(true);
					}
//					((PictureNode)childrenNode).setSelected(false);
				}
			}
			
		});
		
		//鼠标放开，更新选择矩阵的左上角点以及边长
		node.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
//			double nowX = e.getX();
//			double nowY = e.getY();
//			double baseX = selectRectangle.getX();
//			double baseY = selectRectangle.getY();
			
			selectRectangle.setX(0);
			selectRectangle.setY(0);
			
			selectRectangle.setWidth(0);
			selectRectangle.setHeight(0);
			
//			System.out.println(selectRectangle);
			

			//图片和选择矩阵的判断

			
			
			
		});
	}
	private boolean isRectOverlap(ImageLabel  imageLabel) {
		double imageNodeCenterPointX = imageLabel.getLayoutX() + imageLabel.getWidth()/2.0;
		double imageNodeCenterPointY = imageLabel.getLayoutY() + imageLabel.getHeight()/2.0;
		double selectRectangleCenterPointX = selectRectangle.getX() + selectRectangle.getWidth()/2.0;
		double selectRectangleCenterPointY = selectRectangle.getY() + selectRectangle.getHeight()/2.0;
		return Math.abs(imageNodeCenterPointX - selectRectangleCenterPointX) <= (imageLabel.getWidth()/2.0 + selectRectangle.getWidth()/2.0) &&
				Math.abs(imageNodeCenterPointY - selectRectangleCenterPointY) <= (imageLabel.getHeight()/2.0 + selectRectangle.getHeight()/2.0);
	}
}
