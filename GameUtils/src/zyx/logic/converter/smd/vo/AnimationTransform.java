package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public class AnimationTransform
{

	private byte boneId;
	private Vector3f pos;
	private Quaternion rot;

	public AnimationTransform(byte boneId, Vector3f pos, Vector3f rot)
	{
		this.boneId = boneId;
		this.pos = new Vector3f(pos);
		this.rot = EulerToQuat.transform(rot);
	}
	
	public byte getBoneId()
	{
		return boneId;
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(boneId);
		out.writeFloat(pos.x);
		out.writeFloat(pos.y);
		out.writeFloat(pos.z);
		out.writeFloat(rot.x);
		out.writeFloat(rot.y);
		out.writeFloat(rot.z);
		out.writeFloat(rot.w);
	}
}
