package zyx.game.behavior.misc;

import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.utils.FloatMath;

public class JiggleBehavior extends Behavior
{

	private float x;
	private float y;
	private float z;

	private float px;
	private float py;
	private float pz;

	private float scale;
	private float randomOffset;
	private final boolean movement;

	public JiggleBehavior()
	{
		this(false);
	}
	
	public JiggleBehavior(boolean movement)
	{
		super(BehaviorType.JIGGLE);

		randomOffset = FloatMath.random() * 10000;
		this.movement = movement;
	}

	@Override
	public void initialize()
	{
		gameObject.getPosition(true, HELPER_POS);
		
		px = HELPER_POS.x;
		py = HELPER_POS.y;
		pz = HELPER_POS.z;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		x += elapsedTime * 0.025f;
		y += elapsedTime * 0.04f;
		z += elapsedTime * -0.015f;

		float d = timestamp + randomOffset;

		float posX = px + FloatMath.sin(d * 0.001f) * 2;
		float posY = py + FloatMath.cos(d * 0.01f) * 2;
		float posZ = pz + FloatMath.cos(d * 0.01f);

		scale = posX / 5f;
		if (scale == 0)
		{
			scale = 0.1f;
		}

		
//		gameObject.setScale(scale, scale, scale);
		gameObject.setRotation(x, y, z);
		if (movement)
		{
			gameObject.setPosition(true, posX, posY, posZ);
		}
	}

}
