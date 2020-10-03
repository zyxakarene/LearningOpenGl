package zyx.game.scene.dev.showcase;

import zyx.game.components.screen.debug.DebugPanel;
import zyx.game.scene.dev.DebugScene;

public class GuiInteractionScene extends DebugScene
{

	private DebugPanel debugPanel;

	@Override
	protected void onInitialize()
	{
		addPlayerControls();
		
		debugPanel = new DebugPanel();
		stage.hudLayer.addChild(debugPanel);
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();
		
		if (debugPanel != null)
		{
			debugPanel.dispose();
			debugPanel = null;
		}
	}

	
}
