package zyx.opengl.stencils;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_KEEP;
import static org.lwjgl.opengl.GL11.GL_REPLACE;
import static org.lwjgl.opengl.GL14.GL_INCR_WRAP;

public class StencilControl
{

	public static void disableStencils()
	{
		GL11.glDisable(GL11.GL_STENCIL_TEST);
	}

	public static void enableStencils()
	{
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilFunc(GL11.GL_ALWAYS, 0xFF, 0xFF);
		GL11.glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
		
		GL11.glStencilMask(0xFF);
	}
	
	public static void drawToLayer(StencilLayer layer)
	{
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilMask(0xFF);
		
		GL11.glStencilFunc(GL11.GL_ALWAYS, 0xFF, 0xFF);
		GL11.glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
	}
	
	public static void maskOutLayers(StencilLayer layer)
	{
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilMask(0xFF);
		
		GL11.glStencilFunc(GL11.GL_EQUAL, 0, 0xff);
		GL11.glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
	}
}
