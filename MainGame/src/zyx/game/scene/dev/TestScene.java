package zyx.game.scene.dev;

import java.util.ArrayList;
import zyx.engine.scene.Scene;
import zyx.game.behavior.camera.CameraUpdateViewBehavior;
import zyx.game.behavior.freefly.FreeFlyBehavior;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.GameObject;
import zyx.game.components.MeshObject;

public class TestScene extends Scene
{

	private ArrayList<MeshObject> objects;
	private AnimatedMesh knight;

	public TestScene()
	{
		objects = new ArrayList<>();
	}

	@Override
	protected void onPreloadResources()
	{
		preloadResource("flat_bg");
	}

	@Override
	protected void onInitialize()
	{
		for (int i = 0; i < 1; i++)
		{
			MeshObject model = new MeshObject();
			model.load("mesh.box");
			model.setX(-20);
//			model.setY(FloatMath.random() * 300);

			world.addChild(model);
			objects.add(model);
		}
		
		knight = new AnimatedMesh();
		knight.load("mesh.knight.knight");
		knight.setAnimation("attack");
		world.addChild(knight);
		
		MeshObject player = new MeshObject();
		player.addBehavior(new FreeFlyBehavior());
		player.addBehavior(new CameraUpdateViewBehavior());

		objects.add(player);

		world.addChild(player);

	}

//	@Override
//	protected MainHud createHud()
//	{
//		return new MainHud();
//	}

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

		if (knight != null)
		{
			knight.dispose();
			knight = null;
		}
		
		objects.clear();
		objects = null;
	}
}
