package zyx.opengl.textures;

import org.lwjgl.opengl.GL30;

public class BufferBinder
{

	private static int currentBufferId = -1;

	public static void bindBuffer(int bufferId)
	{
		if (currentBufferId != bufferId)
		{
			currentBufferId = bufferId;
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, currentBufferId);
		}
	}
}
