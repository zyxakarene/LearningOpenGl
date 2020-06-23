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
	
	private String name;
	private boolean looping;
	private int startFrame;
	private int endFrame;
	private short blendTime;
	private ArrayList<AnimationFrame> animFrames = new ArrayList<>();
	private AnimationFrame currentFrame;
	private boolean withinRange;
	
	private HashMap<Byte, Bone> boneIdMapFromMesh;
	private HashMap<String, Bone> boneNameMapFromAnimation;

	public SmdAnimationHandler(String name, boolean looping, Bone animationRootBone, int startFrame, int endFrame, short blendTime)
	{
		this.name = name;
		this.looping = looping;
		this.startFrame = startFrame;
		this.endFrame = endFrame;
		this.blendTime = blendTime;
		pos = new Vector3f();
		rot = new Vector3f();
		
		boneNameMapFromAnimation = animationRootBone.toBoneNameMap();
	}

	@Override
	public void handleLine(String line)
	{
		String[] split = line.split(" ");
		
		if (line.startsWith("time"))
		{
			short time = Short.parseShort(split[1]);
			
			withinRange = time >= startFrame && time <= endFrame;
			
			if (withinRange)
			{
				time -= startFrame;
				
				currentFrame = new AnimationFrame(time);
				animFrames.add(currentFrame);
			}
		}
		else if (withinRange)
		{
			byte animationBoneId = Byte.parseByte(split[0]);
			
			float x = Float.parseFloat(split[1]);
			float y = Float.parseFloat(split[2]);
			float z = Float.parseFloat(split[3]);
			
			float rotX = Float.parseFloat(split[4]);
			float rotY = Float.parseFloat(split[5]);
			float rotZ = Float.parseFloat(split[6]);
			
			String boneName = boneIdMapFromMesh.get(animationBoneId).getName();
			Bone meshBone = boneNameMapFromAnimation.get(boneName);
			
			pos.set(x, y, z);
			rot.set(rotX, rotY, rotZ);
			AnimationTransform transform = new AnimationTransform(meshBone.getId(), pos, rot);
			
			currentFrame.addTransform(transform);
		}
	}

	@Override
	public Animation getResult()
	{
		AnimationFrame[] animationFrames = new AnimationFrame[animFrames.size()];
		animFrames.toArray(animationFrames);
		
		return new Animation(name, looping, blendTime, animationFrames);
	}

	@Override
	public void setData(Object data)
	{
		Bone rootBone = (Bone) data;
		boneIdMapFromMesh = rootBone.toBoneMap();
	}

}
