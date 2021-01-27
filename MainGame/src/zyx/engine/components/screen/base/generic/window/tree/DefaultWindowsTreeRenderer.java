package zyx.engine.components.screen.base.generic.window.tree;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.generic.window.WindowsTextfield;
import zyx.opengl.textures.bitmapfont.alignment.HAlignment;

public class DefaultWindowsTreeRenderer<TData> extends DisplayObjectContainer
{
	private DisplayObject display;
	
	protected TData data;

	final void setData(TData data)
	{
		this.data = data;
		
		if (display != null)
		{
			display.removeFromParent(true);
		}
		
		display = createDisplayObject(data);
		addChild(display);
	}

	protected DisplayObject createDisplayObject(TData data)
	{
		float height = getRendererHeight();
		
		WindowsTextfield textfield = new WindowsTextfield(data.toString());
		textfield.setSize(200, height);
		textfield.setHAlign(HAlignment.LEFT);
		
		return textfield;
	}
	
	protected float getRendererHeight()
	{
		return 16;
	}
	
	protected String getIcon()
	{
		return "container";
	}
}
