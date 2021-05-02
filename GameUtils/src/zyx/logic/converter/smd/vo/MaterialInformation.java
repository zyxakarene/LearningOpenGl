package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import zyx.logic.converter.smd.control.json.JsonMeshProperties;

public class MaterialInformation
{
	private boolean zWrite;
	private int zTest;
	private int culling;
	private int blendSrc;
	private int blendDst;
	private int priority;
	private byte stencilMode;
	private int stencilLayer;

	public MaterialInformation()
	{
		zWrite = true;
		zTest = GL11.GL_LEQUAL;
		blendSrc = GL11.GL_ONE;
		blendDst = GL11.GL_ZERO;
		priority = 5000;
		stencilMode = 0;
		stencilLayer = 0;
	}

	void save(DataOutputStream out) throws IOException
	{
		out.writeBoolean(zWrite);
		out.writeInt(zTest);
		out.writeInt(culling);
		out.writeInt(blendSrc);
		out.writeInt(blendDst);
		out.writeInt(priority);
		out.writeByte(stencilMode);
		out.writeInt(stencilLayer);
	}

	void SetFrom(JsonMeshProperties meshProperties)
	{
		zWrite = meshProperties.zWrite;
		zTest = meshProperties.zTest;
		culling = meshProperties.culling;
		blendSrc = meshProperties.blendSrc;
		blendDst = meshProperties.blendDst;
		priority = meshProperties.priority;
		stencilMode = meshProperties.stencilMode;
		stencilLayer = meshProperties.stencilLayer;
	}
}
