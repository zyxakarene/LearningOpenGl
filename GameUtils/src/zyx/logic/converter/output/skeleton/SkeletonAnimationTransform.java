package zyx.logic.converter.output.skeleton;

import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.output.ISaveable;

public class SkeletonAnimationTransform implements ISaveable
{

	public byte boneId;
	public Vector3f position;
	public Quaternion rotation;

	public SkeletonAnimationTransform()
	{
		boneId = 0;
		position = new Vector3f();
		rotation = new Quaternion();
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(boneId);

		out.writeFloat(position.x);
		out.writeFloat(position.y);
		out.writeFloat(position.z);

		out.writeFloat(rotation.x);
		out.writeFloat(rotation.y);
		out.writeFloat(rotation.z);
		out.writeFloat(rotation.w);
	}
}
