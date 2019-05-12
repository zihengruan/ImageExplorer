package model;

import controller.MainExplorerController;
import controller.RootController;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class PanelListener {
	Node node;
	MainExplorerController mainExplorerController;
	private Rectangle selectRectangle;
	private boolean isDragged;
	
	public PanelListener(Node node,MainExplorerController mainExplorerController) {
		this.node = node;
		this.mainExplorerController = mainExplorerController;
		selectRectangle = new Rectangle();
		addListener();
	}
	private void addListener() {
		//鼠标按下，初始化选择矩阵的左上角点
		node.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
			isDragged = false;
			double nowX = e.getX();
			double nowY = e.getY();
			selectRectangle.setX(nowX);
			selectRectangle.setY(nowY);
			selectRectangle.setHeight(0);
			selectRectangle.setWidth(0);

		});
		
		node.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent e) -> {
			this.isDragged = true;
		});
		
		//鼠标放开，更新选择矩阵的左上角点以及边长
		node.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
			double nowX = e.getX();
			double nowY = e.getY();
			double baseX = selectRectangle.getX();
			double baseY = selectRectangle.getY();
			
			selectRectangle.setX(Math.min(baseX, nowX));
			selectRectangle.setY(Math.min(baseY, nowY));
			
			selectRectangle.setWidth(Math.abs(baseX - nowX));
			selectRectangle.setHeight(Math.abs(baseY - nowY));
			
//			System.out.println(selectRectangle);
			
			//图片和选择矩阵的判断
			if(this.isDragged) {		
				
//				ImageLabel.clearSelected();
				for (ImageLabel iLabel : Utilities.selectedImage) {
					iLabel.selected.set(false);
					iLabel.setStyle("-fx-background-color:transparent;");
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
