package zyx.engine.components.meshbatch;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.cubemaps.CubemapManager;
import zyx.engine.components.cubemaps.IReflective;
import zyx.utils.FloatMath;
import zyx.utils.interfaces.IUpdateable;
import zyx.utils.math.QuaternionUtils;

public class MeshBatchEntity implements IUpdateable, IReflective
{

	public Vector3f position;
	public Quaternion rotation;
	public float scale;
	public int cubemapIndex;

	String viewId;
	private float px = -1000;
	private float py;
	private float pz;
	private float rx;
	private float ry;
	private float rz;
	private float randomOffset;
	
	private static final Vector3f RADS = new Vector3f();

	public MeshBatchEntity(String view)
	{
		viewId = view;
		
		position = new Vector3f();
		rotation = new Quaternion();
		scale = 1;
		
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

	@Override
	public Vector3f getPosition(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}
		
		out.x = position.x;
		out.y = position.y;
		out.z = position.z;
		
		return out;
	}

	@Override
	public void enableCubemaps()
	{
		CubemapManager.getInstance().addItem(this);
	}

	@Override
	public void disableCubemaps()
	{
		CubemapManager.getInstance().removeItem(this);
	}

	@Override
	public void setCubemapIndex(int index)
	{
		cubemapIndex = index;
	}
}
