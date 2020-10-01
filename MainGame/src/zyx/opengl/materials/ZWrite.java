package zyx.opengl.materials;

import org.lwjgl.opengl.GL11;

public enum ZWrite
{
	ENABLED(true),
	DISABLED(false);
	
	public final boolean enabled;
	
	private static boolean currentEnabledValue;

	private ZWrite(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public void invoke()
	{
		if (currentEnabledValue != enabled)
		{
			currentEnabledValue = enabled;
			GL11.glDepthMask(enabled);
		}
	}

}
