package zyx.logic.converter.output.mesh;

import java.io.DataOutputStream;
import java.io.IOException;
import zyx.logic.converter.output.ISaveable;

public class ZafColliderBox implements ISaveable
{

	public short boneId;
	public ZafColliderTriangle[] triangles;

	public ZafColliderBox()
	{
		boneId = -1;
		triangles = new ZafColliderTriangle[0];
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeShort(boneId);
		out.writeInt(triangles.length);
		for (ZafColliderTriangle triangle : triangles)
		{
			triangle.save(out);
		}
	}
}
