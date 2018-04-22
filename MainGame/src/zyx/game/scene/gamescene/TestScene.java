package zyx.game.scene.gamescene;

import java.util.ArrayList;
import zyx.engine.scene.Scene;
import zyx.game.components.GameObject;
import zyx.utils.FloatMath;

public class TestScene extends Scene
{
	
	private ArrayList<GameObject> objects;

	public TestScene()
	{
		objects = new ArrayList<>();
	}

	@Override
	protected void onInitialize()
	{
		for (int i = 0; i < 10; i++)
		{
			GameObject model = new GameObject();
			model.load("assets/models/box.zaf");
			model.setX(FloatMath.random() * 300);
			model.setY(FloatMath.random() * 300);

			world.addChild(model);
			objects.add(model);
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
