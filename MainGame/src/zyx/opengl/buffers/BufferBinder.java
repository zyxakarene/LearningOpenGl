package zyx.opengl.buffers;

import org.lwjgl.opengl.GL30;

public class BufferBinder
{

	private static Buffer currentBuffer = null;

	public static void bindBuffer(Buffer buffer)
	{
		if (currentBuffer != buffer)
		{
			currentBuffer = buffer;
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, currentBuffer.bufferId);
		}
	}
}
