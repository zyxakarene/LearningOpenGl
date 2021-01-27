package zyx.debug.editor.hierarchy;

import zyx.engine.components.screen.base.generic.window.tree.DefaultWindowsTreeRenderer;
import zyx.engine.components.screen.base.generic.window.tree.WindowsTreeNode;
import zyx.engine.components.world.WorldObject;

public class DebugWorldTreeNode extends WindowsTreeNode<WorldObject>
{

	public DebugWorldTreeNode(WorldObject data)
	{
		super(data);
	}

	@Override
	protected DefaultWindowsTreeRenderer<WorldObject> createRenderer()
	{
		return new DebugHiearchyRenderer();
	}
}
