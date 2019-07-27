package zyx.engine.components.meshbatch;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.interfaces.IUpdateable;

public class MeshBatchEntity implements IUpdateable
{

	static final int INSTANCE_DATA_AMOUNT = 8;

	public Vector3f position;
	public Quaternion rotation;
	public float scale;

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
}
