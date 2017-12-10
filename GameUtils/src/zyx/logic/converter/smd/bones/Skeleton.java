package zyx.logic.converter.smd.bones;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Skeleton implements ISkeleton
{

	private final ArrayList<SkeletonFrame> frames;

	public Skeleton()
	{
		frames = new ArrayList<>();
	}

	public void addFrame(int time)
	{
		frames.add(new SkeletonFrame(time));
	}

	public void addChange(int nodeId, Vector3f pos, Vector3f rotation, Matrix4f matrix)
	{
		SkeletonFrame latest = frames.get(frames.size() - 1);
		latest.addChange(nodeId, pos, rotation, matrix);
	}

	@Override
	public SkeletonFrame[] getFrames()
	{
		return frames.toArray(new SkeletonFrame[0]);
	}

	@Override
	public void addNodes(INode[] nodes)
	{
		for (int i = 0; i < frames.size(); i++)
		{
			frames.get(i).handleNodes(nodes);
		}
	}

}
