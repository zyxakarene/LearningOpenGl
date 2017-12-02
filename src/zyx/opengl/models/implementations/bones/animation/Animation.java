package zyx.opengl.models.implementations.bones.animation;

public class Animation
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
}
