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
            System.out.println("GLError: " + GLU.gluErrorString(errorID));
			
			throw new RuntimeException("GL Error");
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

    public static void enableGLSettings()
    {
//        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }
}
