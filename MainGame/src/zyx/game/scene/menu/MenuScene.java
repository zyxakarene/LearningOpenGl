package zyx.game.scene.menu;

import java.util.ArrayList;
import zyx.engine.components.screen.base.generic.window.WindowsButton;
import zyx.engine.components.screen.base.generic.window.tree.WindowsTree;
import zyx.engine.components.screen.base.generic.window.tree.WindowsTreeNode;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.scene.Scene;
import zyx.engine.scene.loading.WaitingProcess;
import zyx.game.components.screen.hud.BaseHud;
import zyx.game.components.screen.hud.MenuHud;

public class MenuScene extends Scene
{

	private ArrayList<WindowsTreeNode<Integer>> nodeList;
	
	@Override
	protected void onInitialize()
	{
		nodeList = new ArrayList<>();
		addLoadingScreenProcess(new WaitingProcess(15, "Pretending to load"));
		
		WindowsTreeNode<Integer> root = new WindowsTreeNode<>(1);
		
		WindowsTreeNode<Integer> level1_1 = new WindowsTreeNode<>(11);
		WindowsTreeNode<Integer> level1_2 = new WindowsTreeNode<>(12);
		WindowsTreeNode<Integer> level1_3 = new WindowsTreeNode<>(13);
		
		WindowsTreeNode<Integer> level2_1 = new WindowsTreeNode<>(21);
		WindowsTreeNode<Integer> level2_2 = new WindowsTreeNode<>(22);
		
		WindowsTreeNode<Integer> level3_1 = new WindowsTreeNode<>(31);
		
		root.addChild(level1_1);
		root.addChild(level1_2);
		root.addChild(level1_3);
		
		level1_2.addChild(level2_1);
		level1_2.addChild(level2_2);
		
		level1_3.addChild(level3_1);
		
		nodeList.add(root);
		nodeList.add(level1_1);
		nodeList.add(level1_2);
		nodeList.add(level1_3);
		nodeList.add(level2_1);
		nodeList.add(level2_2);
		nodeList.add(level3_1);
		
		WindowsTree<Integer> tree = new WindowsTree<>(root);
		stage.hudLayer.addChild(tree);
		
		WindowsButton addBtn = new WindowsButton("Add");
		addBtn.setWidth(100);
		addBtn.setHeight(100);
		addBtn.setPosition(true, 400, 50);
		addBtn.onButtonClicked.addCallback(this::onAddButtonClicked);
		stage.hudLayer.addChild(addBtn);
	}
	
	private void onAddButtonClicked(InteractableContainer btn)
	{
		int len = nodeList.size();
		int index = (int) (Math.random() * len);
		
		WindowsTreeNode<Integer> node = nodeList.get(index);
		WindowsTreeNode<Integer> child = new WindowsTreeNode<>(index);
		
		nodeList.add(child);
		node.addChild(child);
	}

	@Override
	protected BaseHud createHud()
	{
		return new MenuHud();
	}

}
