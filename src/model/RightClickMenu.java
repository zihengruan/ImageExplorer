package model;

import java.io.File;
import java.util.List;

import action.CopyAction;
import action.CutAction;
import action.DeleteAction;
import action.PasteAction;
import action.RenameAction;
import controller.MainExplorerController;
import controller.MainViewerController;
import controller.RootController;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class RightClickMenu {
	MainExplorerController mainExplorerController;
	ContextMenu contextMenu;

	public RightClickMenu(Node node, MainExplorerController mainEController, boolean choice) {
		this.mainExplorerController = mainEController;
		if (choice) {
			PictureMenu(node);
		}
		nullMenu(node);

	}

	public void PictureMenu(Node node) {
		contextMenu = new ContextMenu();
		MenuItem open = new MenuItem("打开");
		MenuItem copy = new MenuItem("复制");
		MenuItem cut = new MenuItem("剪切");
		MenuItem rename = new MenuItem("重命名");
		MenuItem delete = new MenuItem("删除");

		contextMenu.getItems().addAll(open, copy, delete, cut, rename);

		open.setOnAction(e -> {
			((ImageLabel)node).setSelected(true);
			((MainViewerController)RootController.controllers.get("controller.MainViewerController")).setImage(((ImageLabel)node).getImageFile2());
			if(!RootController.controllers.get("controller.MainViewerController").getStage().isShowing()) {
				((MainViewerController)RootController.controllers.get("controller.MainViewerController")).showStage();;
			}
		});
		copy.setOnAction(e -> {
			new CopyAction();
		});
		cut.setOnAction(e -> {
			new CutAction();
		});
		rename.setOnAction(e -> {
			new RenameAction(mainExplorerController);
		});
		delete.setOnAction(e -> {
			new DeleteAction(mainExplorerController);
		});

		node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			if (e.getButton() == MouseButton.SECONDARY)
				contextMenu.show(node, e.getScreenX(), e.getScreenY());
			else {
				if (contextMenu.isShowing())
					contextMenu.hide();
			}
		});
	}

	public void nullMenu(Node node) {
		ContextMenu mouseRightClickMenu = new ContextMenu();
		MenuItem paste = new MenuItem("粘贴");
		MenuItem all = new MenuItem("全选");
		mouseRightClickMenu.getItems().add(paste);
		mouseRightClickMenu.getItems().add(all);
		paste.setOnAction(e -> {
			new PasteAction(mainExplorerController);
		});
		all.setOnAction(e -> {
			for (Node childrenNode : ((MainExplorerController)RootController.controllers.get("controller.MainExplorerController")).getFlowPane().getChildren()) {
				if (childrenNode instanceof ImageLabel) {
					((ImageLabel) childrenNode).setSelected(true);
				}
				
			}	
		});
		node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			Node clickNode = e.getPickResult().getIntersectedNode();
			if (clickNode instanceof FlowPane && !(clickNode instanceof ImageLabel) && !(clickNode instanceof Text) && e.getButton() == MouseButton.SECONDARY) {// 鼠标点击非图片节点
//				ImageLabelsclearSelected();// 清空已选
				for (ImageLabel iLabel : Utilities.selectedImage) {
					iLabel.selected.set(false);
					iLabel.setPress(false);
//					iLabel.setStyle("-fx-background-color:transparent;");
				}
				
				Utilities.selectedImage.removeAll(Utilities.selectedImage);
				Utilities.selectedfiles.removeAll(Utilities.selectedfiles);
				
				if (e.getButton() == MouseButton.SECONDARY) {// 鼠标右键
					Clipboard clipboard = Clipboard.getSystemClipboard();
					List<File> files = (List<File>) (clipboard.getContent(DataFormat.FILES));
					if (files.size() <= 0) {
						paste.setDisable(true);
					} else {
						paste.setDisable(false);
					}
					mouseRightClickMenu.show(node, e.getScreenX(), e.getScreenY());
				} else {
					if (mouseRightClickMenu.isShowing()) {
						mouseRightClickMenu.hide();
					}
				}
			} else {
				if (mouseRightClickMenu.isShowing()) {
					mouseRightClickMenu.hide();
				}
			}
		});
	}
}
