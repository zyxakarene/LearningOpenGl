package zyx.opengl.buffers;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class BufferBinder
{

	public static final int UNKNOWN_BUFFER = -1;
	
	private static Buffer currentBuffer = null;

	public static void bindBuffer(Buffer buffer)
	{
		if (currentBuffer != buffer)
		{
			if (buffer.bufferId == UNKNOWN_BUFFER)
			{
				throw new RuntimeException("[Error] Attempting to bind an illegal buffer");
			}

			currentBuffer = buffer;
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, currentBuffer.bufferId);
		}
	}
}
