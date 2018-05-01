package zyx.opengl.particles;

import zyx.utils.FloatMath;
import zyx.utils.interfaces.IUpdateable;

public class ParticleEntity implements IUpdateable
{

	protected float x;
	protected float y;
	protected float z;

	public ParticleEntity()
	{
		x = 0;
		y = 0;
		z = 0;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		x = FloatMath.cos(timestamp * 0.01f);
		y = FloatMath.tan(timestamp * 0.01f);
		z = FloatMath.sin(timestamp * 0.01f);
	}
}
