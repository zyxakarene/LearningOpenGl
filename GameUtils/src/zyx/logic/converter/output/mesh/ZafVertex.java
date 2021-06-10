package zyx.logic.converter.output.mesh;

import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.output.ISaveable;

public class ZafVertex implements ISaveable
{

	private int MAX_AMOUNT = 4;

	public Vector3f position;
	public Vector3f normal;
	public Vector2f uv;

	public short boneCount;
	public byte[] indexes;
	public float[] weights;

	public ZafVertex()
	{
		position = new Vector3f();
		normal = new Vector3f();
		uv = new Vector2f();
		indexes = new byte[MAX_AMOUNT];
		weights = new float[MAX_AMOUNT];
		boneCount = 0;
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeFloat(position.x);
		out.writeFloat(position.y);
		out.writeFloat(position.z);

		out.writeFloat(normal.x);
		out.writeFloat(normal.y);
		out.writeFloat(normal.z);

		out.writeFloat(uv.x);
		out.writeFloat(uv.y);
		
		for (int i = 0; i < boneCount; i++)
		{
			out.writeFloat(indexes[i]);
		}
		for (int i = 0; i < boneCount; i++)
		{
			out.writeFloat(weights[i]);
		}
	}
}
