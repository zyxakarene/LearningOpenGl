package zyx.logic.converter.output.skeleton;

import java.io.DataOutputStream;
import java.io.IOException;
import zyx.logic.converter.output.ISaveable;

public class SkeletonBoneInfo implements ISaveable
{

	public byte id;
	public String name;

	public SkeletonBoneInfo()
	{
		id = -1;
		name = "N/A";
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(id);
		out.writeUTF(name);
	}
}
