package zyx.game.behavior.misc;

import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.utils.FloatMath;

public class RotateBehavior extends Behavior
{

	public RotateBehavior()
	{
		super(BehaviorType.ROTATE);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		float d = timestamp;
		
		float z = FloatMath.cos(d * 0.01f);
				
		gameObject.setRotation(0, 0, z);
	}

}
