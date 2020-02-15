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
	private ArrayList<AnimationFrame> animFrames = new ArrayList<>();
	private AnimationFrame currentFrame;
	
	private HashMap<Byte, Bone> boneIdMapFromMesh;
	private HashMap<String, Bone> boneNameMapFromAnimation;

	public SmdAnimationHandler(String name, boolean looping, Bone animationRootBone)
	{
		this.name = name;
		this.looping = looping;
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
			currentFrame = new AnimationFrame(time);
			animFrames.add(currentFrame);
		}
		else
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
		AnimationFrame[] frames = new AnimationFrame[animFrames.size()];
		animFrames.toArray(frames);
		
		return new Animation(name, looping, frames);
	}

	@Override
	public void setData(Object data)
	{
		Bone rootBone = (Bone) data;
		boneIdMapFromMesh = rootBone.toBoneMap();
	}

}
