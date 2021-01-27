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
		int bgColor = getBgColor();
		debugQuad = new Quad(width, height, bgColor);
		addChild(debugQuad);
	}

	@Override
	protected void onSizeChanged()
	{
		debugQuad.setSize(width, height);
	}

	protected int getBgColor()
	{
		return (int) (0xFFFFFF * Math.random());
	}
}
