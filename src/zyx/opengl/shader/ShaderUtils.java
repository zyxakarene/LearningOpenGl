package zyx.opengl.shader;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class ShaderUtils
{

	private static final int BYTES_PER_FLOAT = Float.SIZE / 8;

	/**
	 * Creates a new vertex data attribute link to the shader program
	 *
	 * @param program The shader program to look for the attribute
	 * @param name The name of the attribute to look for
	 * @return an Integer representing the location of the attribute
	 */
	public static int createAttribute(int program, String name)
	{
		return glGetAttribLocation(program, name);
	}

	/**
	 * Explains the layout of the vertex data, so each attribute knows which floats in each vertex to look at.
	 *
	 * @param attribute The attribute int to link
	 * @param components How many floats this attribute has
	 * @param stride How many floats there are per vertex
	 * @param offset How many floats into the vertex this data starts
	 */
	public static void assignAtribute(int attribute, int components, int stride, int offset)
	{
		glEnableVertexAttribArray(attribute);
		glVertexAttribPointer(attribute, components, GL11.GL_FLOAT, false, BYTES_PER_FLOAT * stride, BYTES_PER_FLOAT * offset);
	}

	/**
	 * Generates a new Vertex shader and returns it right away
	 *
	 * @param source The raw glsl shader
	 * @return The new Vertex shader
	 */
	static int createVertexShader(String source)
	{
		return createShader(source, GL_VERTEX_SHADER);
	}

	/**
	 * Generates a new Fragment shader and returns it right away
	 *
	 * @param source The raw glsl shader
	 * @return The new Fragment shader
	 */
	static int createFragmentShader(String source)
	{
		return createShader(source, GL_FRAGMENT_SHADER);
	}

	/**
	 * Creates and compiles a new shader of type <code>shaderType</code> with the <code>source</code> parameter.
	 *
	 * @param source The raw glsl shader
	 * @param shaderType The type of shader to make. Should be Vertex or Fragment
	 * @return
	 */
	private static int createShader(String source, int shaderType)
	{
		int shader = glCreateShader(shaderType);

		glShaderSource(shader, source);
		glCompileShader(shader);

		checkCompileStatus(shader);

		return shader;
	}

	/**
	 * Ensures that the compiled <code>shaderId</code> is compiled correctly. Should it not be compiled correctly, it will log it and shut down the application
	 *
	 * @param shaderId The shaderId to test for compilation errors
	 */
	private static void checkCompileStatus(int shaderId)
	{
		int shaderCompileStatus = glGetShaderi(shaderId, GL_COMPILE_STATUS);
		if (shaderCompileStatus == GL11.GL_TRUE)
		{
			System.out.println(String.format("shader %s compiled.", shaderId));
		}
		else
		{
			String error = glGetShaderInfoLog(shaderId, 512);
			String errorMsg = String.format("A shader was not compiled correctly:\n%s", error);
			Logger.getLogger("Shader Logger").log(Level.SEVERE, errorMsg);

			System.exit(-1);
		}
	}

	/**
	 * Creates a new shader program.
	 *
	 * @param vertexShader The vertex shader to use
	 * @param fragmentShader The fragment shader to use
	 * @return The newly created shader program
	 */
	static int createProgram(int vertexShader, int fragmentShader)
	{
		int shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);

		glBindFragDataLocation(shaderProgram, 0, "outColor");
		glLinkProgram(shaderProgram);

		return shaderProgram;
	}

	/**
	 * Sets the given shaderProgram as the active one
	 *
	 * @param shaderProgram The program to use
	 */
	static void useShader(int shaderProgram)
	{
		glUseProgram(shaderProgram);
	}
}
