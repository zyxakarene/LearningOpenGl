package zyx.opengl.models.loading.meshes;

import java.io.DataInputStream;
import java.io.IOException;
import zyx.opengl.materials.*;

class MaterialObject implements IMaterialObject
{
	private ZWrite zWrite;
	private ZTest zTest;
	private Culling culling;
	private BlendMode blend;
	private int priority;

	MaterialObject()
	{
	}

	void read(DataInputStream in) throws IOException
	{
		boolean zWriteValue = in.readBoolean();
		int zTestValue = in.readInt();
		int cullingValue = in.readInt();
		
		int srcModeValue = in.readInt();
		int dstModeValue = in.readInt();
		int priorityValue = in.readInt();
		
		zWrite = zWriteValue ? ZWrite.ENABLED : ZWrite.DISABLED;
		zTest = ZTest.fromGlValue(zTestValue);
		culling = Culling.fromGlValue(cullingValue);
		blend = BlendMode.fromGlValues(srcModeValue, dstModeValue);
		priority = priorityValue;
	}

	@Override
	public void applyTo(Material material)
	{
		material.blend = blend;
		material.culling = culling;
		material.zTest = zTest;
		material.zWrite = zWrite;
		material.priority = priority;
	}
}
