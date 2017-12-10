package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public class AnimationTransform
{

	private String name;
	private Vector3f pos;
	private Quaternion rot;

	public AnimationTransform(String name, Vector3f pos, Vector3f rot)
	{
		this.name = name;
		this.pos = new Vector3f(pos);
		this.rot = EulerToQuat.transform(rot);
	}
	
	public String getName()
	{
		return name;
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeUTF(name);
		out.writeFloat(pos.x);
		out.writeFloat(pos.y);
		out.writeFloat(pos.z);
		out.writeFloat(rot.x);
		out.writeFloat(rot.y);
		out.writeFloat(rot.z);
		out.writeFloat(rot.w);
	}
}
