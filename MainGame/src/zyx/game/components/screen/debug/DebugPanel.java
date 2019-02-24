package zyx.game.components.screen.debug;

import zyx.engine.components.screen.base.generic.CollapsablePanel;

public class DebugPanel extends CollapsablePanel
{

	public DebugPanel()
	{
		super(300, 500, 0xCCCCCC);
		
		setPosition(true, 50, 50);
	}
}
