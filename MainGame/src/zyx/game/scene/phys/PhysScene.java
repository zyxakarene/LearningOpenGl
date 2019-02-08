package zyx.game.scene.phys;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import zyx.engine.components.world.Collider;
import zyx.engine.components.world.WorldObject;
import zyx.engine.components.world.complexphysics.EntityCollider;
import zyx.engine.scene.Scene;
import zyx.game.components.GameObject;
import zyx.game.components.MeshObject;
import zyx.game.components.world.player.Player;
import zyx.game.controls.input.KeyboardData;
import zyx.opengl.models.implementations.shapes.Sphere;

public class PhysScene extends Scene
{

	private ArrayList<GameObject> objects;

	private Player player;
	
	public PhysScene()
	{
		objects = new ArrayList<>();
	}

	@Override
	protected void onInitialize()
	{
		MeshObject teapot = new MeshObject();
		teapot.load("mesh.teapot");
		
		MeshObject platform = new MeshObject();
		platform.load("mesh.platform");
		
		world.addChild(teapot);
		world.addChild(platform);
		
		objects.add(teapot);
		objects.add(platform);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		world.physics.update(timestamp, 1);
		
		for (GameObject object : objects)
		{
			object.update(timestamp, elapsedTime);
		}
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
		{
			if (player != null)
			{
				objects.remove(player);
				player.dispose();
			}
			
			player = new Player();
			player.setPosition(true, 13, 15, 50);
			
			objects.add(player);
			
			world.addChild(player);
		}
	}

	@Override
	protected void onDraw()
	{
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
