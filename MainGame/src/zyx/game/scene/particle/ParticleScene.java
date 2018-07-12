package zyx.game.scene.particle;

import java.util.ArrayList;
import zyx.engine.resources.ResourceManager;
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
		ResourceManager.getInstance().getResource("mesh.box");
		
		for (int i = 0; i < 1; i++)
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
				model.setScale(0.1f, 0.1f, 0.1f);
			}
			else
			{
				objects.get(i-1).addChild(model);
			}
			
			objects.add(model);
			ParticleSystem localSystem1 = new ParticleSystem();
			localSystem1.load("assets/effects/particle.zpf");
			localSystem1.setX(10);
			model.addChild(localSystem1);
			
			ParticleSystem localSystem2 = new ParticleSystem();
			localSystem2.load("assets/effects/particle.zpf");
			localSystem2.setX(20);
			model.addChild(localSystem2);
			
			ParticleSystem worldSystem1 = new ParticleSystem();
			worldSystem1.load("assets/effects/world.zpf");
			worldSystem1.setX(-10);
			model.addChild(worldSystem1);
			
			ParticleSystem worldSystem2 = new ParticleSystem();
			worldSystem2.load("assets/effects/world.zpf");
			worldSystem2.setX(-20);
			model.addChild(worldSystem2);
			
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
