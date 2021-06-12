package zyx.logic.converter.output.skeleton;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import zyx.logic.converter.output.ISaveable;

public class SkeletonAnimation implements ISaveable
{

	public String name;
	public boolean looping;
	public short blendTime;
	public ArrayList<SkeletonAnimationFrame> frames;

	public SkeletonAnimation()
	{
		name = "N/A";
		looping = false;
		blendTime = 0;
		frames = new ArrayList<>();
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeUTF(name);
		out.writeBoolean(looping);
		out.writeShort(blendTime);
		out.writeShort(frames.size());
		for (SkeletonAnimationFrame frame : frames)
		{
			frame.save(out);
		}
	}
}
