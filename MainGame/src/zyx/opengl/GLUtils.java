package zyx.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GLUtils
{

	public static void errorCheck()
	{
		int errorID;
		while ((errorID = GL11.glGetError()) != GL11.GL_NO_ERROR)
		{
			String msg = String.format("GLError: [%s]", GLU.gluErrorString(errorID));
			throw new RuntimeException(msg);
		}
	}

	public static void cullBack()
	{
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void cullFront()
	{
		GL11.glCullFace(GL11.GL_FRONT);
	}

	public static void enableDepthTest()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public static void disableDepthTest()
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public static void enableCulling()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	public static void disableCulling()
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	public static void enableGLSettings()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA); 
	}
}
