package zyx.logic.converter.smd.reader;

import org.lwjgl.util.vector.Vector3f;

public class SmdBoundsTriangleHandler implements ISmdHandler
{

	private Vector3f min;
	private Vector3f max;

	public SmdBoundsTriangleHandler()
	{
		min = new Vector3f(0, 0, 0);
		max = new Vector3f(0, 0, 0);
	}

	@Override
	public void handleLine(String line)
	{
		String[] split = line.split(" ");
		if (split.length == 1)
		{
			return;
		}

		float x = Float.parseFloat(split[1]);
		float y = Float.parseFloat(split[2]);
		float z = Float.parseFloat(split[3]);

		min.x = (x < min.x) ? x : min.x;
		min.y = (y < min.y) ? y : min.y;
		min.z = (z < min.z) ? z : min.z;
		
		max.x = (x > max.x) ? x : max.x;
		max.y = (y > max.y) ? y : max.y;
		max.z = (z > max.z) ? z : max.z;
	}

	@Override
	public Object getResult()
	{
		return new Response(min, max);
	}

	@Override
	public void setData(Object data)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public class Response
	{
		public Vector3f min;
		public Vector3f max;

		public Response(Vector3f min, Vector3f max)
		{
			this.min = min;
			this.max = max;
		}
	}
}
