package zyx.game.components.screen.debug;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.generic.window.Window;
import zyx.engine.components.screen.base.generic.window.WindowsButton;
import zyx.engine.components.screen.base.generic.window.WindowsCheckbox;
import zyx.engine.components.screen.base.generic.window.list.CustomRenderer;
import zyx.engine.components.screen.base.generic.window.list.WindowsList;
import zyx.engine.components.screen.base.generic.window.scroll.WindowsScrollView;
import zyx.engine.components.screen.image.Image;

public class DebugPanel extends Window
{
	public DebugPanel()
	{
		super(300, 500);
	
		WindowsButton btn = new WindowsButton("Omg a button!");
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
		
		Image sample = new Image();
		sample.load("sample");
		sample.setSize(50, 100);
		sample.setY(230);
		
//		WindowsButton scrollContent = new WindowsButton("Look at me mom, I am a windows 95 UI");
//		scrollContent.setWidth(50);
//		scrollContent.setHeight(350);
//		scrollContent.addChild(sample);
//		scroll.setView(scrollContent);
		
		WindowsList<Integer> list = new WindowsList<>();
		list.setRenderer(CustomRenderer.class);
		list.setData(new Integer[]{1,2,3,4,5,6,7,8,9,0});
		list.setPosition(true, 0, 32);
		scroll.setView(list);
		
		setPosition(true, 0, 0);
	}
}
