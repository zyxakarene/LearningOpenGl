package zyx.logic.converter.output.skeleton;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import zyx.logic.converter.output.ISaveable;

public class SkeletonAnimationFrame implements ISaveable
{
	public short frame;
	public ArrayList<SkeletonAnimationTransform> transforms;

	public SkeletonAnimationFrame()
	{
		frame = 0;
		transforms = new ArrayList<>();
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeShort(frame);
		out.writeShort(transforms.size());
		for (SkeletonAnimationTransform transform : transforms)
		{
			transform.save(out);
		}
	}
}
