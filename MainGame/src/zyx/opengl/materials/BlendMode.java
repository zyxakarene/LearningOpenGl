package zyx.opengl.materials;

import org.lwjgl.opengl.GL11;

public class BlendMode
{

	public static final BlendMode PARTICLES = new BlendMode(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	public static final BlendMode NORMAL = new BlendMode(GL11.GL_ONE, GL11.GL_ZERO);
	public static final BlendMode ALPHA = new BlendMode(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	public static final BlendMode ADDITIVE = new BlendMode(GL11.GL_ONE, GL11.GL_ONE);

	public static BlendMode[] values = new BlendMode[]
	{
		PARTICLES, NORMAL, ALPHA, ADDITIVE
	};

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
	
	public static BlendMode fromGlValues(int srcMode, int dstMode)
	{
		for (BlendMode value : values)
		{
			if (value.srcFactor == srcMode && value.dstFactor == dstMode)
			{
				return value;
			}
		}
		
		return new BlendMode(srcMode, dstMode);
	}
}
