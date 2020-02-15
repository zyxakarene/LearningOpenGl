package zyx.game.scene.dev;

import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.components.MeshObject;
import zyx.opengl.GLUtils;
import zyx.opengl.particles.ParticleSystem;
import zyx.utils.FloatMath;

public class ParticleScene extends DebugScene
{

	public ParticleScene()
	{
	}

	@Override
	protected void onInitialize()
	{
		addPlayerControls();
		
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
			localSystem1.load("particles.particle2");
			localSystem1.setX(10);
			model.addChild(localSystem1);
			
			ParticleSystem localSystem2 = new ParticleSystem();
			localSystem2.load("particles.particle2");
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
}
