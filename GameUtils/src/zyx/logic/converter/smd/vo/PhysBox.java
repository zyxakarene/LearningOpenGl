package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PhysBox
{	
	public ArrayList<PhysTriangle> triangles = new ArrayList<>();
	private short boneId = -1;
	
	public void save(DataOutputStream out) throws IOException
	{
		out.writeShort(boneId);
		
		out.writeInt(triangles.size());
		for (PhysTriangle triangle : triangles)
		{
			triangle.save(out);
		}
	}
	
	public void setBoneId(short value)
	{
		if (boneId == -1)
		{
			boneId = value;
		}
		else if (boneId != value)
		{
			throw new RuntimeException("Physbox has more than one bone assigned!");
		}
	}
	
	public void addTriangle(PhysTriangle triangle)
	{
		triangles.add(triangle);
	}
}
