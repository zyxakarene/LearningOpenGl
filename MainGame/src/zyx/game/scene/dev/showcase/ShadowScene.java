package zyx.game.scene.dev.showcase;

import zyx.game.components.SimpleMesh;
import zyx.game.scene.dev.DebugScene;

public class ShadowScene extends DebugScene
{

	@Override
	protected void onInitialize()
	{
		addPlayerControls();

		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				SimpleMesh dragon = new SimpleMesh();
				dragon.load("mesh.dragon");
				dragon.setPosition(true, i * 80, j * 80, 40);

				SimpleMesh platform = new SimpleMesh();
				platform.load("mesh.platform");
				platform.setPosition(true, i * 80, j * 80, 0);

				world.addChild(dragon);
				world.addChild(platform);
				
				objects.add(dragon);
				objects.add(platform);
			}
		}
	}
}
