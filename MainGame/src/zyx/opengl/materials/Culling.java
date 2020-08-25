package zyx.opengl.materials;

import org.lwjgl.opengl.GL11;

public enum Culling
{
	FRONT(GL11.GL_FRONT, true),
	BACK(GL11.GL_BACK, true),
	ALL(GL11.GL_FRONT_AND_BACK, true),
	NONE(GL11.GL_BACK, false);
	
	public final int glValue;
	public final boolean enabled;
	
	private static boolean currentEnabledValue;

	private Culling(int glValue, boolean enabled)
	{
		this.glValue = glValue;
		this.enabled = enabled;
	}
	
	public void invoke()
	{
		if (currentEnabledValue != enabled)
		{
			currentEnabledValue = enabled;
			if (enabled)
			{
				GL11.glEnable(GL11.GL_CULL_FACE);
			}
			else
			{
				GL11.glDisable(GL11.GL_CULL_FACE);
			}
		}
	}
}
