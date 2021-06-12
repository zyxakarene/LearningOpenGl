package zyx.logic.converter.output.mesh;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import zyx.logic.converter.output.ISaveable;

public class ZafColliderBox implements ISaveable
{

	public short boneId;
	public ArrayList<ZafColliderTriangle> triangles;

	public ZafColliderBox()
	{
		boneId = -1;
		triangles = new ArrayList<>();
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeShort(boneId);
		out.writeInt(triangles.size());
		for (ZafColliderTriangle triangle : triangles)
		{
			triangle.save(out);
		}
	}
}
