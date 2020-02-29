package zyx;

import zyx.engine.GameStarter;
import zyx.game.debug.DebugConnection;
import zyx.game.scene.SceneType;

public class SceneMain
{

	private static final boolean SHOW_DEBUG_RESOURCES = true;

	public static void main(String[] args)
	{
		java.awt.EventQueue.invokeLater(new GameStarter(SceneType.MENU));
		
		if (SHOW_DEBUG_RESOURCES)
		{
			DebugConnection remotedebugger = new DebugConnection();
			Thread remoteThread = new Thread(remotedebugger, "RemoteDebuggerConnection");
			remoteThread.setPriority(2);
			remoteThread.start();
		}
	}

}
