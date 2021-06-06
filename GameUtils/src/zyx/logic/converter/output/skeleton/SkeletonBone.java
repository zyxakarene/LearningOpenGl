package zyx.logic.converter.output.skeleton;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.output.ISaveable;

public class SkeletonBone implements ISaveable
{

	public byte id;
	public Vector3f restPos;
	public Quaternion restRot;
	public ArrayList<SkeletonBone> childBones;

	public SkeletonBone()
	{
		id = 0;
		restPos = new Vector3f();
		restRot = new Quaternion();
		childBones = new ArrayList<>();
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(id);
		
		out.writeFloat(restPos.x);
		out.writeFloat(restPos.y);
		out.writeFloat(restPos.z);
		
		out.writeFloat(restRot.x);
		out.writeFloat(restRot.y);
		out.writeFloat(restRot.z);
		out.writeFloat(restRot.w);
		
		out.writeByte(childBones.size());
		for (SkeletonBone bone : childBones)
		{
			bone.save(out);
		}
	}
}
