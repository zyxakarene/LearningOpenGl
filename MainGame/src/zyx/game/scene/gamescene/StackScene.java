package zyx.game.scene.gamescene;

import java.util.ArrayList;
import zyx.engine.scene.Scene;
import zyx.game.components.GameObject;
import zyx.utils.FloatMath;

public class StackScene extends Scene
{
	
	private GameObject platform;
	private GameObject child;

	public StackScene()
	{
		
	}

	@Override
	protected void onInitialize()
	{
		platform = new GameObject();
		platform.setY(100);
		platform.setZ(-50);
		platform.load("assets/models/platform.zaf");
		
		child = new GameObject();
		child.load("assets/models/box.zaf");
		
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
		platform.dispose();
		child.dispose();
		
		platform = null;
		child = null;
	}
	
}
