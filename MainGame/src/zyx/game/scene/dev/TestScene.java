package zyx.game.scene.dev;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import zyx.engine.scene.Scene;
import zyx.game.behavior.camera.CameraUpdateViewBehavior;
import zyx.game.behavior.freefly.FreeFlyBehavior;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.GameObject;
import zyx.game.components.MeshObject;
import zyx.game.controls.input.KeyboardData;
import zyx.utils.FloatMath;

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
		for (int i = 0; i < 0; i++)
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

		if (KeyboardData.data.isDown(Keyboard.KEY_SPACE))
		{
			AnimatedMesh tempKnight = new AnimatedMesh();
			tempKnight.setX((FloatMath.random() * 200) - 100);
			tempKnight.setY((FloatMath.random() * 200) - 100);
			tempKnight.setRotZ(FloatMath.random() * 360);
			
			tempKnight.load("mesh.knight.knight");
			
			if (Math.random() > 0.5)
			{
				tempKnight.setAnimation("attack");
			}
			else
			{
				tempKnight.setAnimation("walk");	
			}
			world.addChild(tempKnight);
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
