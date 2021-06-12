package zyx.logic.converter.output.mesh;

import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.output.ISaveable;

public class ZafColliderTriangle implements ISaveable
{

	public Vector3f v1;
	public Vector3f v2;
	public Vector3f v3;
	public Vector3f normal;

	public ZafColliderTriangle()
	{
		v1 = new Vector3f();
		v2 = new Vector3f();
		v3 = new Vector3f();
		normal = new Vector3f();
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeFloat(v1.x);
		out.writeFloat(v1.y);
		out.writeFloat(v1.z);

		out.writeFloat(v2.x);
		out.writeFloat(v2.y);
		out.writeFloat(v2.z);

		out.writeFloat(v3.x);
		out.writeFloat(v3.y);
		out.writeFloat(v3.z);

		out.writeFloat(normal.x);
		out.writeFloat(normal.y);
		out.writeFloat(normal.z);
	}
}
