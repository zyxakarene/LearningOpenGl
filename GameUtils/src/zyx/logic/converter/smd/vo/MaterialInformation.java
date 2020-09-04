package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.opengl.GL11;

public class MaterialInformation
{
	private boolean zWrite;
	private int zTest;
	private int culling;
	private int blendSrc;
	private int blendDst;
	private int priority;

	public MaterialInformation()
	{
		zWrite = true;
		zTest = GL11.GL_LEQUAL;
		blendSrc = GL11.GL_ONE;
		blendDst = GL11.GL_ZERO;
		priority = 5000;
	}

	void save(DataOutputStream out) throws IOException
	{
		out.writeBoolean(zWrite);
		out.writeInt(zTest);
		out.writeInt(culling);
		out.writeInt(blendSrc);
		out.writeInt(blendDst);
		out.writeInt(priority);
	}
}
