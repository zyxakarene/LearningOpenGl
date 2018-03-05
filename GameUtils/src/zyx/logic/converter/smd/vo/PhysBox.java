package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PhysBox
{
	public ArrayList<PhysTriangle> triangles = new ArrayList<>();
	
	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(triangles.size());
		for (PhysTriangle triangle : triangles)
		{
			triangle.save(out);
		}
	}
}
