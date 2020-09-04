package zyx.opengl.materials;

import org.lwjgl.opengl.GL11;

public enum ZTest
{
	LESS(GL11.GL_LESS),
	EQUALS(GL11.GL_EQUAL),
	LESS_EQUAL(GL11.GL_LEQUAL),
	GREATER(GL11.GL_GREATER),
	NOT_EQUAL(GL11.GL_NOTEQUAL),
	GREATER_EQUAL(GL11.GL_GEQUAL),
	ALWAYS(GL11.GL_ALWAYS);
	
	public static final ZTest[] values = values();
	
	public final int glValue;
	
	private static ZTest currentTest;

	private ZTest(int glValue)
	{
		this.glValue = glValue;
	}
	
	public void invoke()
	{
		if (currentTest != this)
		{
			currentTest = this;
			GL11.glDepthFunc(glValue);
		}
	}
	
	public static ZTest fromGlValue(int testValue)
	{
		for (ZTest value : values)
		{
			if (value.glValue == testValue)
			{
				return value;
			}
		}
		
		return LESS_EQUAL;
	}
}
