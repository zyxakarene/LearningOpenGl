package zyx;

import zyx.engine.GameStarter;
import zyx.game.scene.SceneType;

public class SceneMain
{

	public static void main(String[] args)
	{
		java.awt.EventQueue.invokeLater(new GameStarter(SceneType.GAME));
	}

}
