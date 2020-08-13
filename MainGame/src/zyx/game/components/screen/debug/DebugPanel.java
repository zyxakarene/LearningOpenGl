package zyx.game.components.screen.debug;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.generic.window.Window;
import zyx.engine.components.screen.base.generic.window.WindowsButton;
import zyx.engine.components.screen.base.generic.window.WindowsCheckbox;
import zyx.engine.components.screen.base.generic.window.scroll.WindowsScrollView;
import zyx.engine.components.screen.image.Image;

public class DebugPanel extends Window
{
	public DebugPanel()
	{
		super(300, 500);
	
		WindowsButton btn = new WindowsButton("Omg I am a button!");
		btn.setWidth(100);
		btn.setHeight(32);
		addChild(btn);
		btn.setPosition(true, 32, 100);
		
		WindowsCheckbox chck = new WindowsCheckbox();
		addChild(chck);
		chck.setPosition(true, 32, 132);

		WindowsScrollView scroll = new WindowsScrollView(100, 100);
		scroll.setPosition(true, 32, 132 + 16);
		addChild(scroll);
		
		WindowsButton scrollContent = new WindowsButton("a a a a aohsd9u ahsd9ua hsdash dasdh 9asdhu");
		scrollContent.setWidth(50);
		scrollContent.setHeight(350);
		scroll.setView(scrollContent);
		
		
		setPosition(true, 0, 0);
	}
}
