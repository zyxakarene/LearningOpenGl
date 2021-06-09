package zyx.logic.converter.output.mesh;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.output.ISaveable;

public class ZafColliders implements ISaveable
{

	public Vector3f boundingBoxMin;
	public Vector3f boundingBoxMax;
	public ArrayList<ZafColliderBox> boxes;

	public ZafColliders()
	{
		boundingBoxMin = new Vector3f();
		boundingBoxMax = new Vector3f();
		boxes = new ArrayList<>();
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeFloat(boundingBoxMin.x);
		out.writeFloat(boundingBoxMin.y);
		out.writeFloat(boundingBoxMin.z);
		
		out.writeFloat(boundingBoxMax.x);
		out.writeFloat(boundingBoxMax.y);
		out.writeFloat(boundingBoxMax.z);
		
		out.writeInt(boxes.size());
		for (ZafColliderBox box : boxes)
		{
			box.save(out);
		}
	}
}
