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
			model.setX(FloatMath.random() * 10);
			model.setY(FloatMath.random() * 10);
			model.setScale(true, 0.1f, 0.1f, 0.1f);

			world.addChild(model);
			objects.add(model);
		}

		ParticleManager.getInstance().add(new ParticleSystem());
		GLUtils.errorCheck();
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		ParticleManager.getInstance().update(timestamp, elapsedTime);
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
