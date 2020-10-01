package zyx.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import zyx.utils.cheats.Print;

public class GLUtils
{

	public static void errorCheck()
	{
		int errorID;
		while ((errorID = GL11.glGetError()) != GL11.GL_NO_ERROR)
		{
			String msg = String.format("GLError: [%s]", GLU.gluErrorString(errorID));
			Print.err(msg);
			new RuntimeException().printStackTrace();
			System.exit(-1);
		}
	}

	public static void setWireframe(boolean value)
	{
		if (value)
		{
			GL11.glLineWidth(3);
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		}
		else
		{
			GL11.glLineWidth(1);
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
	}

	public static void enableGLSettings()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ZERO);
	}

	public static void clearView()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
	}
}
