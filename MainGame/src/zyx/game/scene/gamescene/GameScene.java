package zyx.game.scene.gamescene;

import java.util.ArrayList;
import zyx.engine.scene.Scene;
import zyx.game.components.GameObject;
import zyx.utils.FloatMath;

public class GameScene extends Scene
{
	
	private ArrayList<GameObject> objects;

	public GameScene()
	{
		objects = new ArrayList<>();
	}

	@Override
	protected void onInitialize()
	{
		for (int i = 0; i < 1; i++)
		{
			GameObject knight = new GameObject();
			knight.load("assets/models/worm/worm.zaf");
			knight.setAnimation("wiggle");
			knight.setX(FloatMath.random() * 300);
			knight.setY(FloatMath.random() * 300);

			world.addChild(knight);
			objects.add(knight);
		}
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		for (GameObject object : objects)
		{
			object.update(timestamp, elapsedTime);
		}
	}
	
	@Override
	protected void onDispose()
	{
		for (GameObject object : objects)
		{
			object.dispose();
		}
		
		objects.clear();
		objects = null;
	}
	
}
