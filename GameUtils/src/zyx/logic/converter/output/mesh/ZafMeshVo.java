package zyx.logic.converter.output.mesh;

import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.output.ISaveable;

public class ZafMeshVo implements ISaveable
{

	public ZafSubMesh[] subMeshes;
	public ZafColliders colliders;
	public Vector3f centerPosition;
	public float radius;
	public String skeleton;

	public ZafMeshVo()
	{
		subMeshes = new ZafSubMesh[0];
		colliders = new ZafColliders();
		centerPosition = new Vector3f();
		radius = 0;
		skeleton = "skeleton.default";
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(subMeshes.length);
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
