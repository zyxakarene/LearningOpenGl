package zyx.game.scene.dragon;

import java.util.ArrayList;
import zyx.engine.components.world.GameLight;
import zyx.engine.scene.Scene;
import zyx.game.components.GameObject;
import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.components.MeshObject;
import zyx.opengl.GLUtils;
import zyx.utils.FloatMath;

public class DragonScene extends Scene
{
	private ArrayList<GameObject> gameObjects;

	public DragonScene()
	{
		gameObjects = new ArrayList<>();
	}

	@Override
	protected void onPreloadResources()
	{
		preloadResource("flat_bg");
	}

	@Override
	protected void onInitialize()
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				MeshObject dragon = new MeshObject();
				dragon.setScale(0.33f, 0.33f, 0.33f);
				dragon.load("mesh.dragon");
				
				if (i == 5 && j == 5)
				{
					world.addChild(dragon);
				}

				MeshObject platform = new MeshObject();
				platform.load("mesh.platform");
				world.addChild(platform);

				gameObjects.add(dragon);
				gameObjects.add(platform);

				dragon.setX(i * 30f);
				platform.setX(i * 60f);
				
				dragon.setY(j * 30f);
				platform.setY(j * 60f);
				
				dragon.setZ(20f);
				platform.setZ(-10f);
				
//				dragon.addBehavior(new JiggleBehavior());
			}
		}
		
		
		
		for (int i = 0; i < 10; i++)
		{
			GameObject lightContainer = new GameObject();
			GameLight light = new GameLight((int) (0xFFFFFF * Math.random()), 100);

			lightContainer.addChild(light);
			world.addChild(lightContainer);

			float x = -20f + (40f * FloatMath.random());
			float y = -20f + (40f * FloatMath.random());
			float z = (25f * FloatMath.random());
			lightContainer.setPosition(true, x, y, z);
			GLUtils.errorCheck();
			
			lightContainer.addBehavior(new JiggleBehavior());
			
			gameObjects.add(lightContainer);
		}
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		for (int i = 0; i < gameObjects.size(); i++)
		{
			GameObject obj = gameObjects.get(i);
			obj.update(timestamp, elapsedTime);
		}
	}

	@Override
	protected void onDispose()
	{
		for (int i = 0; i < gameObjects.size(); i++)
		{
			GameObject obj = gameObjects.get(i);
			obj.dispose();
		}
		
		gameObjects.clear();
		gameObjects = null;
	}
}
