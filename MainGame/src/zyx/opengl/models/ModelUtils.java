package zyx.opengl.models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.*;
import zyx.opengl.GLUtils;
import zyx.opengl.shaders.ShaderManager;
import zyx.utils.cheats.Print;

class ModelUtils
{

	/**
	 * Generates a new buffer object. For example a VBO or EBO
	 *
	 * @return the newly created bufferObject
	 */
	static int generateBufferObject()
	{
		return GL15.glGenBuffers();
	}

	/**
	 * Binds a VBO to be active
	 *
	 * @param vbo The VBO to bind
	 */
	static void bindBufferObject_Array(int vbo)
	{
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
	}

	/**
	 * Uploads the data to the currently bound VBO
	 *
	 * @param data The vertex data to upload
	 */
	static void fillVBO_Static(float[] data)
	{
		FloatBuffer buffer = BufferWrapper.toBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	/**
	 * Uploads the data to the currently bound VBO
	 *
	 * @param data The vertex data to upload
	 */
	static void fillVBO_Dynamic(float[] data)
	{
		FloatBuffer buffer = BufferWrapper.toBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW);
	}

	/**
	 * Binds an EBO to be active
	 *
	 * @param ebo The EBO to bind
	 */
	static void bindBufferObject_Element(int ebo)
	{
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);
	}

	/**
	 * Uploads the data to the currently bound EBO
	 *
	 * @param data The element data to upload
	 */
	static void fillEBO_Static(int[] data)
	{
		IntBuffer buffer = BufferWrapper.toBuffer(data);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	/**
	 * Generates a new VAO
	 *
	 * @return The newly created Vertex Array
	 */
	static int generateVertexArray()
	{
		return GL30.glGenVertexArrays();
	}

	/**
	 * Binds a VAO to be active
	 *
	 * @param vao The VAO to bind
	 */
	static void bindVertexArray(int vao)
	{
		GL30.glBindVertexArray(vao);
	}

	/**
	 * Draws what the vao parameter contains with the amount of elements given
	 *
	 * @param vao The VAO to draw
	 * @param elementCount How many elements the VAO contains
	 */
	static void drawElements(int vao, int elementCount)
	{
		GL30.glBindVertexArray(vao);
		GL11.glDrawElements(GL11.GL_TRIANGLES, elementCount, GL11.GL_UNSIGNED_INT, 0);
	}

	/**
	 * Draws what the vao parameter contains with the amount of elements given
	 *
	 * @param vao The VAO to draw
	 * @param elementCount How many elements the VAO contains
	 * @param instanceCount How many instances to draw
	 */
	static void drawInstancedElements(int vao, int elementCount, int instanceCount)
	{
		GL30.glBindVertexArray(vao);
		GL31.glDrawElementsInstanced(GL11.GL_TRIANGLES, elementCount, GL11.GL_UNSIGNED_INT, 0, instanceCount);
	}

	static int addAttribute(int shaderProgram, String attributeName, int components, int stride, int offset)
	{
		int positionAttrib = GL20.glGetAttribLocation(shaderProgram, attributeName);
		
		if (positionAttrib >= 0)
		{
			GL20.glEnableVertexAttribArray(positionAttrib);
			GL20.glVertexAttribPointer(positionAttrib, components, GL11.GL_FLOAT, false, Float.BYTES * stride, Float.BYTES * offset);
		}
		else
		{
			String msg = "[Warning] Vertex attribute \"%s\" was not found in %s - (It might be unused)";
			String shader = ShaderManager.INSTANCE.getNameFromProgram(shaderProgram);
			Print.out(String.format(msg, attributeName, shader));
		}
		
		return positionAttrib;
	}

	static void addInstanceAttribute(int shaderProgram, String attributeName, int components, int stride, int offset)
	{
		int positionAttrib = addAttribute(shaderProgram, attributeName, components, stride, offset);
		if (positionAttrib >= 0)
		{
			GL33.glVertexAttribDivisor(positionAttrib, 1);
		}
	}

	static void disposeModel(int vao, int vbo, int ebo)
	{
		GL15.glDeleteBuffers(vbo);
		GL15.glDeleteBuffers(ebo);
		GL30.glDeleteVertexArrays(vao);
	}

	static void disposeInstancedModel(int vao, int vbo, int ebo, int instanceVbo)
	{
		disposeModel(vao, vbo, ebo);
		GL15.glDeleteBuffers(instanceVbo);
	}
}
