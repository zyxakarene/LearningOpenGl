package zyx.logic.converter.smd.reader;

import java.util.HashMap;
import java.util.Map;
import zyx.logic.UtilsLogger;
import zyx.logic.converter.smd.vo.Bone;

public class SmdNodeHandler implements ISmdHandler
{

	
	private final HashMap<Byte, Bone> nodes;
	private final HashMap<Bone, Byte> parentIds;
	
	private Bone rootBone;

	public SmdNodeHandler()
	{
		nodes = new HashMap<>();
		parentIds = new HashMap<>();
	}

	@Override
	public void handleLine(String line)
	{
		String[] split = line.split(" ");
		byte nodeId = Byte.parseByte(split[0]);
		String name = split[1];
		name = name.replace("\"", "");
		
		byte parentId = Byte.parseByte(split[2]);
		
		Bone bone = new Bone(nodeId, name);
		
		if (parentId == -1)
		{
			rootBone = bone;
		}
		
		nodes.put(nodeId, bone);
		parentIds.put(bone, parentId);
		
		UtilsLogger.log("Created node: " + nodeId + " " + name + " " + parentId);
	}

	@Override
	public Bone getResult()
	{
		setAllParentNodes();
		return rootBone;
	}

	private void setAllParentNodes()
	{
		for (Map.Entry<Bone, Byte> entry : parentIds.entrySet())
		{
			Bone bone = entry.getKey();
			Byte parentId = entry.getValue();

			UtilsLogger.log("Matching " + bone + " with " + parentId);
			
			Bone parent = nodes.get(parentId);
			
			if (parent != null)
			{
				parent.addChild(bone);
			}
		}

		parentIds.clear();
		nodes.clear();
	}

	@Override
	public void setData(Object data)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}