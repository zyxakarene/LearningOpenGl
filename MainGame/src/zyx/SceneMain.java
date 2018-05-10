package zyx;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.GameStarter;
import zyx.game.scene.SceneType;

public class SceneMain
{

	public static void main(String[] args)
	{
		java.awt.EventQueue.invokeLater(new GameStarter(SceneType.STACK));
	}

}
