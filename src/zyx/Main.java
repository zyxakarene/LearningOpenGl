package zyx;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import zyx.opengl.SetupOpenGlCommand;
import zyx.opengl.shader.ShaderManager;
import zyx.opengl.shader.ShaderUtils;
import zyx.opengl.shader.implementations.Shader;
import zyx.opengl.shader.source.ShaderSourceLoader;
import zyx.utils.GameConstants;
import zyx.utils.exceptions.GLErrors;

public class Main
{

	private static final String VERTEX_SHADER = ShaderSourceLoader.getSource("vertex.shader");
	private static final String FRAGMENT_SHADER = ShaderSourceLoader.getSource("fragment.shader");

	public static void main(String[] args)
	{
		new SetupOpenGlCommand().execute();

		load();

		GLErrors.errorCheck();
		
		while (!Display.isCloseRequested())
		{
			Display.update();
			Display.sync(GameConstants.FPS);

			draw();
			
			GLErrors.errorCheck();
		}
	}

	private static void draw()
	{
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);

		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
	}

	private static void load()
	{
		ShaderManager.INSTANCE.initialize();
		ShaderManager.INSTANCE.activate(Shader.BASE);

		float vertices[] =
		{
			-0.5f, 0.5f, // Top-left
			0.5f, 0.5f, // Top-right
			0.5f, -0.5f, // Bottom-right
			-0.5f, -0.5f  // Bottom-left
		};

		int elements[] =
		{
			0, 1, 2,
			2, 3, 0
		};

		int vao = glGenVertexArrays();
		glBindVertexArray(vao);

		int vertexBuffer = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuffer);

		int ebo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);

		int positionAttrib = glGetAttribLocation(ShaderManager.INSTANCE.get(Shader.BASE).program, "position");
		glEnableVertexAttribArray(positionAttrib);
		glVertexAttribPointer(positionAttrib, 2, GL11.GL_FLOAT, false, Float.BYTES * 2, Float.BYTES * 0);
		//2 components, for X and Y
		//2 stride, for 2 floats per vertex
		//0 offset, for starts at 0

		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(vertices.length);
		floatBuffer.put(vertices);
		floatBuffer.flip();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);

		IntBuffer intBuffer = BufferUtils.createIntBuffer(elements.length);
		intBuffer.put(elements);
		intBuffer.flip();
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, intBuffer, GL15.GL_STATIC_DRAW);

	}

}
