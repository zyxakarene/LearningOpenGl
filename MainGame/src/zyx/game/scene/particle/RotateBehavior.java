package zyx.game.scene.particle;

import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.utils.FloatMath;

class RotateBehavior extends Behavior
{

	private float x;
	private float y;
	private float z;

	private float px;
	private float py;
	private float pz;
	
	private float scale;

	public RotateBehavior()
	{
		super(BehaviorType.ROTATER);

		x = FloatMath.random() * 360 * 0;
		y = FloatMath.random() * 360 * 0;
		z = FloatMath.random() * 360 * 0;
		
		px = 0;
		py = 0;
		pz = 0;
		
		scale = 0.1f;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		x += elapsedTime * 0.025f;
		y += elapsedTime * 0.04f;
		z += elapsedTime * -0.015f;

		double d = timestamp;
		
		px = (float) Math.sin(d * 0.001) * 2;
		py = (float) Math.cos(d * 0.01) * 2;
		pz = (float) Math.cos(d * 0.01);
				
		gameObject.setRotation(x, y, z);
		gameObject.setPosition(true, px, py, pz);
	}

}
