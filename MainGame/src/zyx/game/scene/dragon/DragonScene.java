package zyx.game.scene.dragon;

import java.util.ArrayList;
import zyx.engine.components.world.GameLight;
import zyx.engine.scene.Scene;
import zyx.game.components.GameObject;
import zyx.game.components.SimpleMesh;
import zyx.game.behavior.misc.JiggleBehavior;
import zyx.opengl.GLUtils;
import zyx.utils.FloatMath;
import zyx.utils.cheats.DebugPoint;

public class DragonScene extends Scene
{

	private ArrayList<GameObject> lights = new ArrayList<>();
	
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
		
		for (int i = 0; i < 30; i++)
		{
			GameObject lightContainer = new GameObject();
			GameLight light = new GameLight((int) (0xFFFFFF * Math.random()), 1);

			lightContainer.addChild(light);
			world.addChild(lightContainer);

			float x = -20f + (40f * FloatMath.random());
			float y = -10f + (20f * FloatMath.random());
			float z = (25f * FloatMath.random());
			lightContainer.setPosition(true, x, y, z);
			GLUtils.errorCheck();
		}
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		for (int i = 0; i < lights.size(); i++)
		{
			GameObject obj = lights.get(i);
			obj.update(timestamp, elapsedTime);
		}
	}

	@Override
	protected void onDispose()
	{
		for (int i = 0; i < lights.size(); i++)
		{
			GameObject obj = lights.get(i);
			obj.dispose();
		}
		
		mesh.dispose();
		mesh = null;
		
		lights.clear();
		lights = null;
	}
}
