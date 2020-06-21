package zyx.opengl.models.implementations.bones.animation;

import zyx.utils.interfaces.IDisposeable;

public class Animation implements IDisposeable
{
	int length;
	boolean looping;
	
	public String name;
	public AnimationFrame[] frames;
	public int blendDuration;

	public Animation(String name, int length, boolean looping)
	{
		this.name = name;
		this.frames = new AnimationFrame[length];
		this.length = length;
		this.looping = looping;
		this.blendDuration = 500;
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
