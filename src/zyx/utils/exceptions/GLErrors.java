package zyx.utils.exceptions;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GLErrors
{

	public static void errorCheck()
	{
		int errorID = GL11.glGetError();
		if (errorID != GL11.GL_NO_ERROR)
		{
			System.out.println("GLError: " + GLU.gluErrorString(errorID));
		}
	}
}
