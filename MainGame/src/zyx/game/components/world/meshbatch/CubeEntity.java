package zyx.game.components.world.meshbatch;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.meshbatch.MeshBatchEntity;
import zyx.utils.FloatMath;
import zyx.utils.math.QuaternionUtils;

public class CubeEntity extends MeshBatchEntity
{

	private float px = -1000;
	private float py;
	private float pz;
	private float rx;
	private float ry;
	private float rz;
	private float randomOffset;

	private static final Vector3f RADS = new Vector3f();

	public CubeEntity()
	{
		super("meshbatch.simple.box");

		randomOffset = FloatMath.random() * 1000;
		enableCubemaps();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (px == -1000)
		{
			px = position.x;
			py = position.y;
			pz = position.z;
		}

		rx += elapsedTime * 0.025f;
		ry += elapsedTime * 0.04f;
		rz += elapsedTime * -0.015f;

		float d = timestamp + randomOffset;

		position.x = px + FloatMath.sin(d * 0.001f) * 2;
		position.y = py + FloatMath.cos(d * 0.01f) * 2;
		position.z = pz + FloatMath.cos(d * 0.01f);

		RADS.x = FloatMath.DEG_TO_RAD * rx;
		RADS.y = FloatMath.DEG_TO_RAD * ry;
		RADS.z = FloatMath.DEG_TO_RAD * rz;

		QuaternionUtils.toQuat(RADS, rotation);
	}
}
