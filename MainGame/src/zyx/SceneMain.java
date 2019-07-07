package zyx;

import zyx.debug.DebugController;
import zyx.engine.GameStarter;
import zyx.game.scene.SceneType;

public class SceneMain
{
	
	private static final boolean SHOW_DEBUG_RESOURCES = true;
	
	public static void main(String[] args)
	{
		if (SHOW_DEBUG_RESOURCES)
		{
			GameStarter starter = new GameStarter(SceneType.MENU);
			Thread gameThread = new Thread(starter);

			gameThread.start();

			DebugController.show();
		}
		else
		{
			java.awt.EventQueue.invokeLater(new GameStarter(SceneType.MENU));
		}
	}

}
