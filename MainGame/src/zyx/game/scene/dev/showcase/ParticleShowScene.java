package zyx.game.scene.dev.showcase;

import zyx.game.scene.dev.*;
import zyx.engine.components.meshbatch.MeshBatchManager;
import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.components.MeshObject;
import zyx.game.components.world.meshbatch.CubeEntity;
import zyx.opengl.GLUtils;
import zyx.opengl.particles.ParticleSystem;
import zyx.utils.FloatMath;

public class ParticleShowScene extends DebugScene
{

	public ParticleShowScene()
	{
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
				objects.get(i - 1).addChild(model);
			}

			objects.add(model);
			ParticleSystem localSystem1 = new ParticleSystem();
			localSystem1.load("particles.particle2");
			localSystem1.setZ(40);
			model.addChild(localSystem1);

			ParticleSystem worldSystem1 = new ParticleSystem();
			worldSystem1.load("particles.world");
			model.addChild(worldSystem1);

			model.addBehavior(new JiggleBehavior(true));
		}

		addPlayerControls();
	}
}
