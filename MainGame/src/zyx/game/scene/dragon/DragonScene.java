package zyx.game.scene.dragon;

import zyx.engine.scene.Scene;
import zyx.game.components.SimpleMesh;
import zyx.utils.cheats.DebugPoint;

public class DragonScene extends Scene
{

	private SimpleMesh mesh;

	public DragonScene()
	{
	}

	@Override
	protected void onPreloadResources()
	{
		preloadResource("flat_bg");
	}

	@Override
	protected void onInitialize()
	{
		mesh = new SimpleMesh();
		mesh.setScale(0.33f, 0.33f, 0.33f);
//		mesh.setY(20);
//		mesh.setZ(-10);
		mesh.load("mesh.dragon");
		world.addChild(mesh);
		
		DebugPoint.addToScene(0, 0, 0, 0);
	}

	@Override
	protected void onDispose()
	{
		mesh.dispose();
		mesh = null;
	}
}
