package zyx.engine.components.screen.base.docks;

import zyx.engine.components.screen.base.ContainerDock;
import zyx.engine.components.screen.base.Quad;

public class EditorDock extends ContainerDock
{

	private Quad debugQuad;

	public EditorDock(DockType type)
	{
		super(type);
	}

	@Override
	protected void setup()
	{
		debugQuad = new Quad(width, height, (int) (0xFFFFFF * Math.random()));
		addChild(debugQuad);
	}

	@Override
	protected void onSizeChanged()
	{
		debugQuad.setSize(width, height);
	}
}
