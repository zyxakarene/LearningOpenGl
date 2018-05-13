package zyx.game.scene.particle;

import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.utils.FloatMath;

class RotateBehavior extends Behavior
{

	private float x;
	private float y;
	private float z;

	public RotateBehavior()
	{
		super(BehaviorType.ROTATER);

		x = FloatMath.random() * 360 * 1;
		y = FloatMath.random() * 360 * 1;
		z = FloatMath.random() * 360 * 1;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		x += elapsedTime * 0.005f;
		y += elapsedTime * 0.008f;
		z += elapsedTime * -0.003f;

		gameObject.setRotation(x, y, z);
	}

}
