package zyx.engine.components.screen.base.generic.window.list;

import zyx.engine.components.screen.image.Image;
import zyx.utils.FloatMath;

public class CustomRenderer extends WindowsListRenderer<Integer>
{
	private Image bg;
	
	public CustomRenderer()
	{
		bg = new Image();
		bg.load("sample");
		bg.setSize(50, 20);
		bg.setColor((int) (0xFFFFFF * FloatMath.random()));
		
		addChild(bg);
		
	}

	@Override
	protected void onDataChanged(Integer data)
	{
		bg.setColor((int) (0xFFFFFF * FloatMath.random()));
	}

	@Override
	protected void OnSelectionChanged(boolean selected)
	{
		bg.setColor((int) (0xFFFFFF * FloatMath.random()));
	}

}
