package zyx.opengl.materials;

import org.lwjgl.opengl.GL11;

public enum BlendMode
{
	PARTICLES(GL11.GL_SRC_ALPHA, GL11.GL_ONE),
	
	NORMAL(GL11.GL_ONE, GL11.GL_ZERO),
	ALPHA(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA),
	ADDITIVE(GL11.GL_ONE, GL11.GL_ONE);
	
	public final int srcFactor;
	public final int dstFactor;
	
	private static BlendMode currentBlendMode;

	private BlendMode(int srcMode, int dstMode)
	{
		this.srcFactor = srcMode;
		this.dstFactor = dstMode;
	}
	
	public void invoke()
	{
		if (currentBlendMode != this)
		{
			currentBlendMode = this;
			GL11.glBlendFunc(srcFactor, dstFactor);
		}
	}
}
