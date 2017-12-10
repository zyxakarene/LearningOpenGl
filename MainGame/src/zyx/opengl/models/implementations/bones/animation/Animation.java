package zyx.opengl.models.implementations.bones.animation;

import zyx.utils.interfaces.IDisposeable;

public class Animation implements IDisposeable
{
	int length;
	boolean loopable;
	
	public String name;
	public AnimationFrame[] frames;

	public Animation(String name, int length)
	{
		this.name = name;
		this.frames = new AnimationFrame[length];
		this.length = length;
		this.loopable = true;
	}
	
	public void setFrame(int frame, AnimationFrame animationFrame)
	{
		animationFrame.frame = frame;
		frames[frame] = animationFrame;
	}

	@Override
	public void dispose()
	{
		int len = frames.length;
		for (int i = 0; i < len; i++)
		{
			frames[i].dispose();
		}
		
		name = null;
		frames = null;
	}
}
