package zyx.opengl.lighs;

import org.lwjgl.util.vector.Vector3f;

public class LightsProvider
{
	public static Vector3f[] lightPositions;
	public static Vector3f[] lightColors;
	public static int[] lightPowers;
	
	public static int lastLightCount;
	
	static
	{
		lightPositions = new Vector3f[0];
		lightColors = new Vector3f[0];
		lightPowers = new int[0];
		lastLightCount = -1;
	}
	
	public static void setLights(ILight[] lights)
	{
		if (lastLightCount != lights.length)
		{
			lastLightCount = lights.length;
			
			lightColors = new Vector3f[lastLightCount];
			lightPositions = new Vector3f[lastLightCount];
			lightPowers = new int[lastLightCount];
			
			for (int i = 0; i < lastLightCount; i++)
			{
				lightColors[i] = new Vector3f();
				lightPositions[i] = new Vector3f();
			}
		}
		
		for (int i = 0; i < lastLightCount; i++)
		{
			ILight light = lights[i];

			if (light == null)
			{
				lightPowers[i] = 0;
			}
			else
			{
				light.getLightPosition(lightPositions[i]);
				light.getColorVector(lightColors[i]);
				lightPowers[i] = light.getPower();
			}
		}
	}
}
