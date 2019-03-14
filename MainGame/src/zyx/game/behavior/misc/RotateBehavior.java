package zyx.game.behavior.misc;

import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.utils.FloatMath;

public class RotateBehavior extends Behavior
{
	private float z;

	public RotateBehavior()
	{
		super(BehaviorType.ROTATE);
		
		z = 0f;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		float d = timestamp;
		
		z += elapsedTime * 0.1f;
				
		gameObject.setRotation(0, 0, z);
	}

}
