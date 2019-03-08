package zyx.opengl.shaders.implementations;

import java.nio.FloatBuffer;
import java.util.HashMap;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

class UniformUtils
{

	private static Matrix4f[] matrixArray = new Matrix4f[1];

	private static final HashMap<Integer, FloatBuffer> BUFFERS = new HashMap<>();

	/**
	 * Returns an integer pointing to the uniform of the given name in the given program
	 *
	 * @param program The program to search for the uniform
	 * @param name The name of the uniform to search for
	 * @return The ID of the found uniform
	 */
	static int createUniform(int program, String name)
	{
		return GL20.glGetUniformLocation(program, name);
	}

	/**
	 * Uploads the given matrix into the given uniform
	 *
	 * @param uniform The uniform to upload to
	 * @param matrix The data to upload
	 */
	static void setUniformMatrix(int uniform, Matrix4f matrix)
	{
		matrixArray[0] = matrix;
		setUniformMatrix(uniform, matrixArray);
	}

	/**
	 * Uploads the given matrix array into the given uniform
	 *
	 * @param uniform The uniform to upload to
	 * @param matrices The data to upload
	 */
	static void setUniformMatrix(int uniform, Matrix4f[] matrices)
	{
		int size = 16 * matrices.length;

		FloatBuffer buff = getBufferOfSize(size);
		for (Matrix4f matrix : matrices)
		{
			matrix.store(buff);
		}
		buff.flip();

		GL20.glUniformMatrix4(uniform, false, buff);
	}

	/**
	 * Uploads an array of Vec3s to the given uniform.
	 *
	 * @param uniform The uniform to upload to
	 * @param data The data to upload
	 */
	static void setUniformArrayF(int uniform, Vector3f[] data)
	{
		int size = 3 * data.length;

		FloatBuffer buff = getBufferOfSize(size);
		for (Vector3f entry : data)
		{
			if (entry != null)
			{
				entry.store(buff);
			}
			else
			{
				buff.put(0);
				buff.put(0);
				buff.put(0);
			}
		}
		buff.flip();

		GL20.glUniform3(uniform, buff);
	}

	static void setUniform2F(int uniform, float x, float y)
	{
		GL20.glUniform2f(uniform, x, y);
	}

	static void setUniformInt(int uniform, int value)
	{
		GL20.glUniform1i(uniform, value);
	}

	static void setUniformFloat(int uniform, float value)
	{
		GL20.glUniform1f(uniform, value);
	}

	static void setUniform3F(int uniform, float x, float y, float z)
	{
		GL20.glUniform3f(uniform, x, y, z);
	}

	static void setUniform4F(int uniform, float x, float y, float z, float w)
	{
		GL20.glUniform4f(uniform, x, y, z, w);
	}

	private static FloatBuffer getBufferOfSize(int size)
	{
		FloatBuffer buff;

		if (BUFFERS.containsKey(size) == false)
		{
			buff = BufferUtils.createFloatBuffer(size);
			BUFFERS.put(size, buff);
		}
		else
		{
			buff = BUFFERS.get(size);
		}

		buff.clear();

		return buff;
	}
}
