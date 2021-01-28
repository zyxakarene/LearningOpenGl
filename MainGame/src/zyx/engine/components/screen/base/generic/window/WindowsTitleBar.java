package zyx.engine.components.screen.base.generic.window;

import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.image.Scale9Image;
import zyx.engine.curser.GameCursor;

public class WindowsTitleBar extends DisplayObjectContainer
{
	public WindowsTitleBar(int width)
	{
		Scale9Image image = new Scale9Image();
		image.load("titlebar");
		image.setSize(width, 32);
		addChild(image);
		
		WindowsTextfield textfield = new WindowsTextfield("DebugWindow");
		addChild(textfield);
		textfield.setSize(width, 32);
		
		hoverIcon = GameCursor.HAND;
		
	}
}
