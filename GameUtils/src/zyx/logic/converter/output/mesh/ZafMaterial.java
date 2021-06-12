package zyx.logic.converter.output.mesh;

import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import zyx.logic.converter.output.ISaveable;

public class ZafMaterial implements ISaveable
{

	public boolean zWrite;
	public int zTest;
	public int culling;
	public int blendSrc;
	public int blendDst;
	public int priority;
	public byte stencilMode;
	public int stencilLayer;

	public ZafMaterial()
	{
		zWrite = true;
		zTest = GL11.GL_LESS;
		culling = GL11.GL_BACK;
		blendSrc = GL11.GL_ONE;
		blendDst = GL11.GL_ZERO;
		priority = 500;
		stencilMode = 0;
		stencilLayer = 0;
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(zTest);
		out.writeInt(zTest);
		out.writeInt(culling);
		out.writeInt(blendSrc);
		out.writeInt(blendDst);
		out.writeInt(priority);
		out.writeByte(stencilMode);
		out.writeInt(stencilLayer);
	}
}
