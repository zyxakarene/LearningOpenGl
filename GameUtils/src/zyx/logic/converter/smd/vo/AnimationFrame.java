package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AnimationFrame
{

	public short frame;
	public ArrayList<AnimationTransform> transforms;

	public AnimationFrame(short frame)
	{
		this.frame = frame;
		transforms = new ArrayList<>();
	}
	
	public void addTransform(AnimationTransform transform)
	{
		transforms.add(transform);
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeShort(frame);
		out.writeShort(transforms.size());
		for (AnimationTransform transform : transforms)
		{
			transform.save(out);
		}
	}
}
