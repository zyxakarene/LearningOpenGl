package zyx.game.scene.gamescene;

import zyx.engine.scene.Scene;
import zyx.game.components.MeshObject;

public class StackScene extends Scene
{
	
	private MeshObject platform;
	private MeshObject child;

	public StackScene()
	{
		
	}

	@Override
	protected void onInitialize()
	{
		platform = new MeshObject();
		platform.setY(100);
		platform.setZ(-50);
		platform.load("mesh.platform");
		
		child = new MeshObject();
		child.load("mesh.box");
		
		world.addChild(platform);
		platform.addChild(child);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		child.setRotZ(child.getRotZ() + 0.5f);
		child.update(timestamp, elapsedTime);
	}
	
	@Override
	protected void onDispose()
	{
		if (platform != null)
		{
			platform.dispose();
			platform = null;
		}
		
		if (child != null)
		{
			child.dispose();
			child = null;
		}
	}
	
}
