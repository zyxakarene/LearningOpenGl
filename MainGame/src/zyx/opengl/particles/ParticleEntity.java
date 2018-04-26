package zyx.opengl.particles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.utils.FloatMath;
import zyx.utils.interfaces.IUpdateable;

public class ParticleEntity implements IUpdateable
{

	private static final Matrix4f MODEL_MATRIX = ParticleShader.MATRIX_MODEL;
	private static final Vector3f HELPER_VEC = new Vector3f();

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
		x += (FloatMath.random() > 0.5f) ? 0.1f : -0.1f;
		y += (FloatMath.random() > 0.5f) ? 0.1f : -0.1f;
		z = FloatMath.sin(timestamp * 0.01f);
	}

	void transform()
	{
		MODEL_MATRIX.setIdentity();
		
		HELPER_VEC.set(x, y, z);
		MODEL_MATRIX.translate(HELPER_VEC);
	}
}
