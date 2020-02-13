package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;

public class Animation
{
	private String name;
	private short length;
	private boolean looping;
	private AnimationFrame[] frames;

	public Animation(String name, boolean looping, AnimationFrame[] frames)
	{
		this.name = name;
		this.looping = looping;
		this.length = (short) frames.length;
		this.frames = frames;
	}
	
	public void save(DataOutputStream out) throws IOException
	{
		out.writeUTF(name);
		out.writeBoolean(looping);
		out.writeShort(length);
		
		for (AnimationFrame frame : frames)
		{
			frame.save(out);
		}
	}
}
