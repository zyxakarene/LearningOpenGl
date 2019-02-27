package zyx;

import zyx.debug.DebugController;
import zyx.engine.GameStarter;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.scene.SceneType;
import zyx.utils.cheats.Print;
import zyx.utils.tasks.ScheduledTask;
import zyx.utils.tasks.TaskScheduler;
import zyx.utils.tasks.dev.HeavyTask;

public class SceneMain
{
	
	private static final boolean SHOW_DEBUG_RESOURCES = true;
	
	public static void main(String[] args)
	{
		if (SHOW_DEBUG_RESOURCES)
		{
			GameStarter starter = new GameStarter(SceneType.MATRIX);
			Thread gameThread = new Thread(starter);

			gameThread.start();

			DebugController.show();
		}
		else
		{
			java.awt.EventQueue.invokeLater(new GameStarter(SceneType.MATRIX));
		}
	}

}
