package zyx.opengl.buffers;

import java.util.ArrayList;
import org.lwjgl.opengl.GL30;
import zyx.engine.utils.callbacks.ICallback;

public class BufferBinder
{

	public static final int UNKNOWN_BUFFER = -1;
	
	private static Buffer currentBuffer = null;
	
	private static final ArrayList<ICallback<Buffer>> BUFFER_LISTENER = new ArrayList<>();

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
			
			for (ICallback<Buffer> listener : BUFFER_LISTENER)
			{
				listener.onCallback(buffer);
			}
		}
	}
	
	public static Buffer getCurrentBuffer()
	{
		return currentBuffer;
	}
	
	public static void addListener(ICallback<Buffer> listener)
	{
		BUFFER_LISTENER.add(listener);
	}
	
	public static void removeListener(ICallback<Buffer> listener)
	{
		BUFFER_LISTENER.remove(listener);
	}
}
