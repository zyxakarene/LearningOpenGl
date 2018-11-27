package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Vertex
{

	private Vector3f pos;
	private Vector3f norm;
	private Vector2f uv;

	private BoneWeight weights = new BoneWeight();
	private final int index;

	public Vertex(Vector3f pos, Vector3f norm, Vector2f uv, int index)
	{
		this.pos = new Vector3f(pos);
		this.norm = new Vector3f(norm);
		this.uv = new Vector2f(uv);
		this.index = index;
	}

	public Vector3f getPos()
	{
		return pos;
	}
	
	public void addWeight(byte index, float weight)
	{
		weights.addWeight(index, weight);
	}
	
	public void save(DataOutputStream out) throws IOException
	{
		out.writeFloat(pos.x);
		out.writeFloat(pos.y);
		out.writeFloat(pos.z);
		out.writeFloat(norm.x);
		out.writeFloat(norm.y);
		out.writeFloat(norm.z);
		out.writeFloat(uv.x);
		out.writeFloat(uv.y);
		
		weights.save(out);
	}
	
	
}
