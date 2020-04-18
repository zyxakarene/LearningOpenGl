package zyx.opengl.shaders;

import java.util.Scanner;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import zyx.utils.cheats.Print;

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

		checkCompileStatus(shader, source);

		return shader;
	}

	/**
	 * Ensures that the compiled <code>shaderId</code> is compiled correctly. Should it not be compiled correctly, it will log it and shut down the application
	 *
	 * @param shaderId The shaderId to test for compilation errors
	 */
	private static void checkCompileStatus(int shaderId, String source)
	{
		int shaderCompileStatus = glGetShaderi(shaderId, GL_COMPILE_STATUS);
		if (shaderCompileStatus == GL11.GL_TRUE)
		{
			String msg = String.format("shader %s compiled.", shaderId);
			Print.out(msg);
		}
		else
		{
			Scanner scan = new Scanner(source);
			StringBuilder formatSource = new StringBuilder();
			int lineCount = 1;
			String lineTemplate = "(%s)\t";
			String lineBreak = "\n";
			while (scan.hasNextLine())
			{				
				String line = scan.nextLine();
				formatSource.append(String.format(lineTemplate, lineCount));
				formatSource.append(line);
				formatSource.append(lineBreak);
				
				lineCount++;
			}
			
			int logLength = glGetShaderi(shaderId, GL_INFO_LOG_LENGTH);
			String error = glGetShaderInfoLog(shaderId, logLength);
			String errorMsg = String.format("Shader %s was not compiled correctly:\n%s\n%s", shaderId, error, formatSource);
			
			Print.err(errorMsg);
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
		logProgramInfo("VertexAttach", shaderProgram);
		
		glAttachShader(shaderProgram, fragmentShader);
		logProgramInfo("FragAttach", shaderProgram);

		glBindFragDataLocation(shaderProgram, 0, "outColor");
		logProgramInfo("FragLocation", shaderProgram);
		
		glLinkProgram(shaderProgram);
		logProgramInfo("Linking", shaderProgram);
		
		glValidateProgram(shaderProgram);
		logProgramInfo("Validating", shaderProgram);
		
		int linkStatus = glGetProgrami(shaderProgram, GL_LINK_STATUS);
		if (linkStatus == GL11.GL_TRUE)
		{
			Print.out("Pogram", shaderProgram, "was linked sucesfully");
		}
		else
		{
			String msg = String.format("Program %s could not be linked with shaders %s, %s", shaderProgram, vertexShader, fragmentShader);
			Print.err(msg);
			System.exit(-1);
		}

		int validateStatus = glGetProgrami(shaderProgram, GL_VALIDATE_STATUS);
		if (validateStatus == GL11.GL_TRUE)
		{
			Print.out("Pogram", shaderProgram, "was validated succesfully");
		}
		else
		{
			String msg = String.format("Program %s with shaders %s & %s could not be validated", shaderProgram, vertexShader, fragmentShader);
			Print.err(msg);
			System.exit(-1);
		}
		
		
		
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

	private static void logProgramInfo(String type, int shaderProgram)
	{
		int logLength = glGetProgrami(shaderProgram,GL_INFO_LOG_LENGTH);
		if (logLength > 0)
		{
			String logInfo = glGetProgramInfoLog(shaderProgram, logLength);
			Print.err("Shader program log type:", type, "-", logInfo);
		}
	}
}
