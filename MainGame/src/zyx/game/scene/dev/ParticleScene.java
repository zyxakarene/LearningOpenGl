package zyx.game.scene.dev;

import zyx.game.behavior.misc.JiggleBehavior;
import java.util.ArrayList;
import zyx.engine.scene.Scene;
import zyx.game.components.MeshObject;
import zyx.opengl.GLUtils;
import zyx.opengl.particles.ParticleManager;
import zyx.opengl.particles.ParticleSystem;
import zyx.utils.FloatMath;

public class ParticleScene extends Scene
{

	private ArrayList<MeshObject> objects;

	public ParticleScene()
	{
		objects = new ArrayList<>();
	}

	@Override
	protected void onInitialize()
	{
		for (int i = 0; i < 1; i++)
		{
			MeshObject model = new MeshObject();
			model.load("mesh.box");
			if (i > 0)
			{
				model.setX(FloatMath.random() * 50);
				model.setY(FloatMath.random() * 50);
				model.setZ(FloatMath.random() * 50);
				model.setRotation(0, 90, 0);
			}

			if (i == 0)
			{
				model.setPosition(true, 0, 10, 0);
				world.addChild(model);
				model.setScale(0.1f, 0.1f, 0.1f);
			}
			else
			{
				objects.get(i-1).addChild(model);
			}
			
			objects.add(model);
			ParticleSystem localSystem1 = new ParticleSystem();
			localSystem1.load("particles.particle");
			localSystem1.setX(10);
			model.addChild(localSystem1);
			
			ParticleSystem localSystem2 = new ParticleSystem();
			localSystem2.load("particles.particle");
			localSystem2.setX(20);
			model.addChild(localSystem2);
			
			ParticleSystem worldSystem1 = new ParticleSystem();
			worldSystem1.load("particles.world");
			worldSystem1.setX(-10);
			model.addChild(worldSystem1);
			
			ParticleSystem worldSystem2 = new ParticleSystem();
			worldSystem2.load("particles.world");
			worldSystem2.setX(-20);
			model.addChild(worldSystem2);
			
			model.addBehavior(new JiggleBehavior());
		}
		
		GLUtils.errorCheck();
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		ParticleManager.getInstance().update(timestamp, elapsedTime);
		
		for (MeshObject object : objects)
		{
			object.update(timestamp, elapsedTime);
		}
	}

	@Override
	protected void onDispose()
	{
		ParticleManager.getInstance().dispose();
		
		for (MeshObject object : objects)
		{
			object.dispose();
		}
		
		objects.clear();
		objects = null;
	}
}
