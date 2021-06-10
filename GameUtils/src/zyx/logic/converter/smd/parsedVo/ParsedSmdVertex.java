package zyx.logic.converter.smd.parsedVo;

import java.util.Arrays;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class ParsedSmdVertex
{

	public static final int MAX_BONES = 4;

	public final Vector3f pos;
	public final Vector3f norm;
	public final Vector2f uv;

	public byte boneCount;
	public final byte[] boneIndexes;
	public final float[] boneWeights;

	public ParsedSmdVertex()
	{
		pos = new Vector3f();
		norm = new Vector3f();
		uv = new Vector2f();

		boneCount = 0;
		boneIndexes = new byte[MAX_BONES];
		boneWeights = new float[MAX_BONES];
	}

	@Override
	public String toString()
	{
		return pos + "-" + norm + "-" + uv + "-" + Arrays.toString(boneIndexes) + "-" + Arrays.toString(boneWeights);
	}
}
