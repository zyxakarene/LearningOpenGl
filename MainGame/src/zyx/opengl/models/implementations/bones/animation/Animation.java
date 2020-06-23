package zyx.opengl.models.implementations.bones.animation;

import zyx.utils.interfaces.IDisposeable;

public class Animation implements IDisposeable
{
	int length;
	boolean looping;
	short blendDuration;
	
	public String name;
	public AnimationFrame[] frames;

	public Animation(String name, int length, boolean looping, short blendDuration)
	{
		this.name = name;
		this.frames = new AnimationFrame[length];
		this.length = length;
		this.looping = looping;
		this.blendDuration = blendDuration;
	}
	
	public void setFrame(int frame, AnimationFrame animationFrame)
	{
		animationFrame.frame = frame;
		frames[frame] = animationFrame;
	}

	@Override
	public void dispose()
	{
		for (AnimationFrame frame : frames)
		{
			frame.dispose();
		}
		
		name = null;
		frames = null;
	}
}
