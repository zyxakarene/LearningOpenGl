package zyx.game.scene.particle;

import java.util.ArrayList;
import zyx.engine.scene.Scene;
import zyx.game.components.GameObject;
import zyx.opengl.GLUtils;
import zyx.opengl.particles.ParticleManager;
import zyx.opengl.particles.ParticleSystem;
import zyx.utils.FloatMath;

public class ParticleScene extends Scene
{

	private ArrayList<GameObject> objects;

	public ParticleScene()
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
			if (i > 0)
			{
				model.setX(FloatMath.random() * 50);
				model.setY(FloatMath.random() * 50);
				model.setZ(FloatMath.random() * 50);
				model.setRotation(0, 90, 0);
			}

			if (i == 0)
			{
				world.addChild(model);
				model.setScale(true, 0.1f, 0.1f, 0.1f);
			}
			else
			{
				objects.get(i-1).addChild(model);
			}
			
			objects.add(model);
			ParticleSystem system = new ParticleSystem();
			system.load("assets/effects/particle.txt");
			model.addChild(system);
			
			model.addBehavior(new RotateBehavior());
		}
		
		GLUtils.errorCheck();
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		ParticleManager.getInstance().update(timestamp, elapsedTime);
		
		for (GameObject object : objects)
		{
			object.update(timestamp, elapsedTime);
		}
	}

	@Override
	protected void onDraw()
	{
		ParticleManager.getInstance().draw();
	}

	@Override
	protected void onDispose()
	{
		ParticleManager.getInstance().dispose();
		
		for (GameObject object : objects)
		{
			object.dispose();
		}
		
		objects.clear();
		objects = null;
	}

}
