package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;

public class Animation
{
	private String name;
	private short length;
	private boolean looping;
	private AnimationFrame[] frames;
	private short blendTime;

	public Animation(String name, boolean looping, short blendTime, AnimationFrame[] frames)
	{
		this.name = name;
		this.looping = looping;
		this.length = (short) frames.length;
		this.blendTime = blendTime;
		this.frames = frames;
	}
	
	public void save(DataOutputStream out) throws IOException
	{
		out.writeUTF(name);
		out.writeBoolean(looping);
		out.writeShort(blendTime);
		out.writeShort(length);
		
		for (AnimationFrame frame : frames)
		{
			frame.save(out);
		}
	}
}
