package zyx.opengl.particles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.utils.FloatMath;
import zyx.utils.interfaces.IUpdateable;

public class ParticleEntity implements IUpdateable
{

	private static final Vector3f POS_VECTOR = ParticleShader.VECTOR_POS;

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
//		x += (FloatMath.random() > 0.5f) ? 0.1f : -0.1f;
//		y += (FloatMath.random() > 0.5f) ? 0.1f : -0.1f;
		z = FloatMath.sin(timestamp * 0.01f);
	}

	void transform()
	{
		POS_VECTOR.set(x, y, z);
	}
}
