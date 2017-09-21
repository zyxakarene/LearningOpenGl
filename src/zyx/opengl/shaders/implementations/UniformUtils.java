package zyx.opengl.shaders.implementations;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

class UniformUtils
{

	private static final FloatBuffer MATRIX_FLOAT_BUFFER = BufferUtils.createFloatBuffer(16);

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
		MATRIX_FLOAT_BUFFER.clear();
		matrix.store(MATRIX_FLOAT_BUFFER);
		MATRIX_FLOAT_BUFFER.flip();

		GL20.glUniformMatrix4(uniform, false, MATRIX_FLOAT_BUFFER);
	}
}
