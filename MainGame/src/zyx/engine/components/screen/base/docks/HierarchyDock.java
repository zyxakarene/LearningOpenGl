package zyx.engine.components.screen.base.docks;

import zyx.debug.link.DebugInfo;
import zyx.debug.link.DebugWorldObjectLink;
import zyx.engine.components.screen.base.generic.window.scroll.WindowsScrollView;
import zyx.engine.components.screen.base.generic.window.tree.WindowsTree;
import zyx.engine.components.screen.base.generic.window.tree.WindowsTreeNode;
import zyx.engine.components.world.WorldObject;

public class HierarchyDock extends EditorDock
{

	private WindowsTree<WorldObject> tree;
	private DebugWorldObjectLink info;
	private WindowsScrollView scrollView;

	public HierarchyDock()
	{
		super(DockType.Left);
	}

	@Override
	protected void setup()
	{
		super.setup();

		info = DebugInfo.worldObjects;
		WindowsTreeNode<WorldObject> root = info.getGlRootNode();
		tree = new WindowsTree<>(root);

		scrollView = new WindowsScrollView((int) getWidth(), (int) getHeight());
		scrollView.setView(tree);

		addChild(scrollView);
	}

	@Override
	protected void onSizeChanged()
	{
		super.onSizeChanged();
		
		scrollView.resize((int) getWidth(), (int) getHeight());
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
	}

	@Override
	protected int getBgColor()
	{
		return 0;
	}
}
