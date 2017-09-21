package zyx;

import java.io.IOException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import zyx.opengl.SetupOpenGlCommand;
import zyx.opengl.models.implementations.SimpleModel;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.GameConstants;
import zyx.utils.exceptions.GLErrors;

public class Main
{

	private static SimpleModel model;
	private static Texture textureImage;

	public static void main(String[] args) throws IOException
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
		textureImage.release();
	}

	private static void draw()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		model.draw();
	}

	private static void load() throws IOException
	{
		ShaderManager.INSTANCE.initialize();
		ShaderManager.INSTANCE.activate(Shader.BASE);

		model = new SimpleModel();

		String filename = String.format("textures/%s.%s", "sample", "png");
		textureImage = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream(filename), GL11.GL_NEAREST);
		textureImage.bind();
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);		
	}

}
