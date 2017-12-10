package zyx.logic.converter.smd.reader;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.smd.vo.Animation;
import zyx.logic.converter.smd.vo.AnimationFrame;
import zyx.logic.converter.smd.vo.AnimationTransform;
import zyx.logic.converter.smd.vo.Bone;

public class SmdAnimationHandler implements ISmdHandler
{

	private final Vector3f pos, rot;
	
	private String fileName;
	private ArrayList<AnimationFrame> animFrames = new ArrayList<>();
	private AnimationFrame currentFrame;
	private HashMap<Byte, Bone> boneMap;

	public SmdAnimationHandler(String fileName)
	{
		this.fileName = fileName;
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
			currentFrame = new AnimationFrame(time);
			animFrames.add(currentFrame);
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
			String boneName = boneMap.get(nodeId).getName();
			AnimationTransform transform = new AnimationTransform(boneName, pos, rot);
			
			currentFrame.addTransform(transform);
		}
	}

	@Override
	public Animation getResult()
	{
		AnimationFrame[] frames = new AnimationFrame[animFrames.size()];
		animFrames.toArray(frames);
		
		return new Animation(fileName, frames);
	}

	@Override
	public void setData(Object data)
	{
		Bone rootBone = (Bone) data;
		boneMap = rootBone.toBoneMap();
	}

}
