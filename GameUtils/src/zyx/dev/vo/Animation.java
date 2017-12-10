package zyx.dev.vo;

import java.io.DataOutputStream;
import java.io.IOException;

public class Animation
{
	private String name;
	private int length;
	private AnimationFrame[] frames;

	public Animation(String name, AnimationFrame[] frames)
	{
		this.name = name;
		this.length = frames.length;
		this.frames = frames;
	}
	
	public void save(DataOutputStream out) throws IOException
	{
		out.writeUTF(name);
		out.writeInt(length);
		
		for (AnimationFrame frame : frames)
		{
			frame.save(out);
		}
	}
}
