package zyx;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import zyx.opengl.SetupOpenGlCommand;
import zyx.opengl.models.implementations.SimpleModel;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.GameConstants;
import zyx.utils.exceptions.GLErrors;

public class Main
{

	private static SimpleModel model;

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
		
		model.dispose();
	}

	private static void draw()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		model.draw();
	}

	private static void load()
	{
		ShaderManager.INSTANCE.initialize();
		ShaderManager.INSTANCE.bind(Shader.WORLD);

		model = new SimpleModel();
	}

}
