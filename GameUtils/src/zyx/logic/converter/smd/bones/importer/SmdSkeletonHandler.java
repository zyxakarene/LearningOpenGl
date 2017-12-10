package zyx.logic.converter.smd.bones.importer;

import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.smd.bones.Skeleton;

public class SmdSkeletonHandler implements ISmdHandler
{

	private final Vector3f pos, rot;
	private final Skeleton skeleton;

	public SmdSkeletonHandler()
	{
		skeleton = new Skeleton();
		pos = new Vector3f();
		rot = new Vector3f();
	}

	@Override
	public void handleLine(String line)
	{
		String[] split = line.split(" ");
		
		if (line.startsWith("time"))
		{
			int time = Integer.parseInt(split[1]);
			skeleton.addFrame(time);
		}
		else
		{
			int nodeId = Integer.parseInt(split[0]);
			
			float x = Float.parseFloat(split[1]);
			float y = Float.parseFloat(split[2]);
			float z = Float.parseFloat(split[3]);
			
			float rotX = Float.parseFloat(split[4]);
			float rotY = Float.parseFloat(split[5]);
			float rotZ = Float.parseFloat(split[6]);
			
			pos.set(x, y, z);
			rot.set(rotX, rotY, rotZ);
			skeleton.addChange(nodeId, pos, rot, null);
		}
	}

	@Override
	public Object getResult()
	{
		return skeleton;
	}

}
