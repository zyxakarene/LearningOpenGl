package zyx.logic.converter.output.mesh;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.output.ISaveable;

public class ZafMeshVo implements ISaveable
{

	public ArrayList<ZafSubMesh> subMeshes;
	public ZafColliders colliders;
	public Vector3f centerPosition;
	public float radius;
	public String skeleton;

	public ZafMeshVo()
	{
		subMeshes = new ArrayList<>();
		colliders = new ZafColliders();
		centerPosition = new Vector3f();
		radius = 0;
		skeleton = "skeleton.default";
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(subMeshes.size());
		for (ZafSubMesh subMesh : subMeshes)
		{
			subMesh.save(out);
		}
		
		colliders.save(out);
		
		out.writeFloat(centerPosition.x);
		out.writeFloat(centerPosition.y);
		out.writeFloat(centerPosition.z);
		
		out.writeFloat(radius);
		
		out.writeUTF(skeleton);
	}
}
