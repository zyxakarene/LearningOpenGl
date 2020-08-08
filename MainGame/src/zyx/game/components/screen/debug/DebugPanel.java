package zyx.game.components.screen.debug;

import zyx.engine.components.screen.base.generic.CollapsablePanel;
import zyx.engine.components.screen.base.generic.window.Window;
import zyx.engine.components.screen.base.generic.window.WindowsButton;
import zyx.engine.components.screen.base.generic.window.WindowsCheckbox;

public class DebugPanel extends Window
{
	private CollapsablePanel panel;
	
	public DebugPanel()
	{
		super(300, 500);
	
//		panel = new CollapsablePanel(300, 500, 0xCCCCCC);
//		addChild(panel);
		
		WindowsButton btn = new WindowsButton();
		addChild(btn);
		btn.setPosition(true, 32, 100);
		
		WindowsCheckbox chck = new WindowsCheckbox();
		addChild(chck);
		chck.setPosition(true, 32, 132);

		setPosition(true, 0, 0);
	}
}
