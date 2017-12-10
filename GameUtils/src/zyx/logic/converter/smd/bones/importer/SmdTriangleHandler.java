package zyx.logic.converter.smd.bones.importer;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.smd.bones.Triangle;

public class SmdTriangleHandler implements ISmdHandler
{
	private final Vector3f pos;
	private final Vector3f norm;
	private final Vector2f uv;
	
	private final ArrayList<Triangle> triangles;
	private Triangle workingTriangle;

	public SmdTriangleHandler()
	{
		triangles = new ArrayList<>();
		pos = new Vector3f();
		norm = new Vector3f();
		uv = new Vector2f();
	}
	
	@Override
	public void handleLine(String line)
	{
		if (line.equals("undefined") || line.equals("01_-_Default"))
		{
			workingTriangle = new Triangle();
			triangles.add(workingTriangle);
			return;
		}
		
		String[] split = line.split(" ");
		int boneId = Integer.parseInt(split[0]);
		float x = Float.parseFloat(split[1]);
		float y = Float.parseFloat(split[2]);
		float z = Float.parseFloat(split[3]);
		pos.set(x, y, z);

		float normX = Float.parseFloat(split[4]);
		float normY = Float.parseFloat(split[5]);
		float normZ = Float.parseFloat(split[6]);
		norm.set(normX, normY, normZ);

		float u = Float.parseFloat(split[7]);
		float v = Float.parseFloat(split[8]);		
		uv.set(u, v);

		if (split.length < 10)
		{
			System.out.println(line);
		}
		
		workingTriangle.setVertexData(boneId, pos, norm, uv);
		
		if(split.length > 9)
		{
			int boneCount = Integer.parseInt(split[9]);
			for (int i = 0; i < boneCount; i++)
			{
				int boneWeightId = Integer.parseInt(split[10 + (2 * i)]);
				float weight = Float.parseFloat(split[11 + (2 * i)]);
				workingTriangle.addVertexBoneWeight(boneWeightId, weight);
			}
		}
		else
		{
			int boneWeightId = 1;
			float weight = 1f;
			workingTriangle.addVertexBoneWeight(boneWeightId, weight);
		}



		workingTriangle.endVertex();
	}

	@Override
	public Object getResult()
	{
		return triangles.toArray(new Triangle[0]);
	}

}
