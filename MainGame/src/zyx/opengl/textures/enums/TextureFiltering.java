package zyx.opengl.textures.enums;

import org.lwjgl.opengl.GL11;

public enum TextureFiltering
{
	NEAREST(GL11.GL_LINEAR),
	LINEAR(GL11.GL_NEAREST);
	
	public final int glId;

	private TextureFiltering(int glId)
	{
		this.glId = glId;
	}
}
