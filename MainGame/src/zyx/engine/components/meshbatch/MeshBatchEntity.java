package zyx.engine.components.meshbatch;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.cubemaps.CubemapManager;
import zyx.engine.components.cubemaps.IReflective;
import zyx.utils.interfaces.IUpdateable;

public class MeshBatchEntity implements IUpdateable, IReflective
{

	public Vector3f position;
	public Quaternion rotation;
	public float scale;
	
	int cubemapIndex;
	String viewId;

	public MeshBatchEntity(String view)
	{
		viewId = view;
		
		position = new Vector3f();
		rotation = new Quaternion();
		scale = 1;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		
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
