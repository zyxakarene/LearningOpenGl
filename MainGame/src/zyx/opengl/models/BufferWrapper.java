package zyx.opengl.models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

public class BufferWrapper
{

	public static FloatBuffer toBuffer(float[] array)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.flip();

		return buffer;
	}

	public static IntBuffer toBuffer(int[] array)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(array.length);
		buffer.put(array);
		buffer.flip();

		return buffer;
	}

}
