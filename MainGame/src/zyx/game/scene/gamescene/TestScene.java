package zyx.game.scene.gamescene;

import java.util.ArrayList;
import zyx.engine.scene.Scene;
import zyx.game.components.MeshObject;
import zyx.utils.FloatMath;

public class TestScene extends Scene
{
	
	private ArrayList<MeshObject> objects;

	public TestScene()
	{
		objects = new ArrayList<>();
	}

	@Override
	protected void onInitialize()
	{
		for (int i = 0; i < 10; i++)
		{
			MeshObject model = new MeshObject();
			model.load("mesh.box");
			model.setX(FloatMath.random() * 300);
			model.setY(FloatMath.random() * 300);

			world.addChild(model);
			objects.add(model);
		}
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		for (MeshObject object : objects)
		{
			object.update(timestamp, elapsedTime);
		}
	}

	@Override
	protected void onDispose()
	{
		for (MeshObject object : objects)
		{
			object.dispose();
		}
		
		objects.clear();
		objects = null;
	}
	
}
