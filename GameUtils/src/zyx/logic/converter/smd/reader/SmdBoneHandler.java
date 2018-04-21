package zyx.logic.converter.smd.reader;

import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.UtilsLogger;
import zyx.logic.converter.smd.vo.Bone;

public class SmdBoneHandler implements ISmdHandler
{

	private final Vector3f pos, rot;
	private HashMap<Byte, Bone> boneMap;

	public SmdBoneHandler()
	{
		pos = new Vector3f();
		rot = new Vector3f();
	}

	@Override
	public void handleLine(String line)
	{
		String[] split = line.split(" ");
		
		if (line.startsWith("time"))
		{
			//Ignore time! We are building the rest poses
		}
		else
		{
			byte nodeId = Byte.parseByte(split[0]);
			
			float x = Float.parseFloat(split[1]);
			float y = Float.parseFloat(split[2]);
			float z = Float.parseFloat(split[3]);
			
			float rotX = Float.parseFloat(split[4]);
			float rotY = Float.parseFloat(split[5]);
			float rotZ = Float.parseFloat(split[6]);
			
			pos.set(x, y, z);
			rot.set(rotX, rotY, rotZ);
			
			Bone bone = boneMap.get(nodeId);
			UtilsLogger.log("NodeId " + nodeId + " gave bone " + bone);
			bone.setRestData(pos, rot);
		}
	}

	@Override
	public Object getResult()
	{
		return null;
	}

	@Override
	public void setData(Object data)
	{
		Bone root = (Bone) data;
		this.boneMap = root.toBoneMap();
	}

}
