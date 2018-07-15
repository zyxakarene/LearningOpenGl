package zyx;

import zyx.debug.DebugResourceViewer;
import zyx.engine.GameStarter;
import zyx.game.scene.SceneType;

public class SceneMain
{

	public static void main(String[] args)
	{
		GameStarter starter = new GameStarter(SceneType.STACK);
		Thread gameThread = new Thread(starter);
		
		gameThread.start();
		
		new DebugResourceViewer().setVisible(true);
//		java.awt.EventQueue.invokeLater(new GameStarter(SceneType.STACK));
	}

}
