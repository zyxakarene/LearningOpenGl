package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

public class PhysInformation
{

	private boolean hasCustomBoundingBox = false;
	
	private Vector3f minSize = new Vector3f();
	private Vector3f maxSize = new Vector3f();

	private ArrayList<PhysBox> physBoxes = new ArrayList<>();

	public void save(DataOutputStream out) throws IOException
	{
		out.writeFloat(minSize.x);
		out.writeFloat(minSize.y);
		out.writeFloat(minSize.z);

		out.writeFloat(maxSize.x);
		out.writeFloat(maxSize.y);
		out.writeFloat(maxSize.z);

		out.writeInt(physBoxes.size());
		for (PhysBox physBox : physBoxes)
		{
			physBox.save(out);
		}
	}

	void setBoxes(ArrayList<PhysBox> boxes)
	{
		physBoxes.addAll(boxes);
		
		if(hasCustomBoundingBox == false)
		{
			minSize = new Vector3f();
			maxSize = new Vector3f();
			
			for (PhysBox physBox : physBoxes)
			{
				for (PhysTriangle triangle : physBox.triangles)
				{
					testVertex(triangle.v1);
					testVertex(triangle.v2);
					testVertex(triangle.v3);
				}
			}
		}
	}
	
	void setBoundingBox(Vector3f min, Vector3f max)
	{
		hasCustomBoundingBox = true;
		minSize.set(min);
		maxSize.set(max);
	}
	
	private void testVertex(PhysVertex vertex)
	{
		if(vertex.x < minSize.x)
		{
			minSize.x = vertex.x;
		}
		if(vertex.x > maxSize.x)
		{
			maxSize.x = vertex.x;
		}
		
		if(vertex.y < minSize.y)
		{
			minSize.y = vertex.y;
		}
		if(vertex.y > maxSize.y)
		{
			maxSize.y = vertex.y;
		}
		
		if(vertex.z < minSize.z)
		{
			minSize.z = vertex.z;
		}
		if(vertex.z > maxSize.z)
		{
			maxSize.z = vertex.z;
		}
	}
}
