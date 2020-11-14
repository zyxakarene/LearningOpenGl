package zyx.engine.components.screen.base.generic.window.tree;

import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.generic.window.WindowsTextfield;
import zyx.opengl.textures.bitmapfont.alignment.HAlignment;

public class DefaultWindowsTreeRenderer<TData> extends DisplayObjectContainer
{
	protected TData data;
	private WindowsTextfield textfield;

	final void setData(TData data)
	{
		this.data = data;
		
		onSetData();
	}

	protected void onSetData()
	{
		if (textfield != null)
		{
			textfield.removeFromParent(true);
		}
		
		textfield= new WindowsTextfield(data.toString());
		textfield.setSize(200, 16);
		textfield.setHAlign(HAlignment.LEFT);
		
		addChild(textfield);
	}
}
