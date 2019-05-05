package zyx.game.controls.lights;

import java.util.ArrayList;
import java.util.Arrays;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.opengl.lighs.ILight;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.LightingPassShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.GameConstants;
import zyx.utils.cheats.DebugPoint;

public class LightsManager
{

	private static LightsManager instane = new LightsManager();

	public static LightsManager getInstane()
	{
		return instane;
	}

	private ArrayList<ILight> allLights;
	private ILight[] nearestLights;
	private int nearestLightCount;

	private WorldObject viewer;
	private LightSorter lightSorter;
	private LightingPassShader lightShader;

	private LightsManager()
	{
		allLights = new ArrayList<>();
		lightSorter = new LightSorter();

		nearestLightCount = GameConstants.LIGHT_COUNT;
		nearestLights = new ILight[nearestLightCount];
	}

	public void setSource(WorldObject viewer)
	{
		this.viewer = viewer;

		lightSorter.setViewer(viewer);
		lightShader = (LightingPassShader) ShaderManager.INSTANCE.get(Shader.DEFERED_LIGHT_PASS);
	}

	public void addLight(ILight light)
	{
		if (allLights.contains(light) == false)
		{
			allLights.add(light);
		}
	}

	public void removeLight(ILight light)
	{
		allLights.remove(light);
	}

	public void uploadLights()
	{
		lightSorter.reset();

		allLights.sort(lightSorter);

		int lightCount = allLights.size();
		for (int i = 0; i < nearestLightCount; i++)
		{
			if (i < lightCount)
			{
				nearestLights[i] = allLights.get(i);

				Vector3f pos = nearestLights[i].getLightPosition(new Vector3f());
//				DebugPoint.addToScene(pos, 20);
			}
			else
			{
				nearestLights[i] = null;
			}
		}

		lightShader.uploadLights(nearestLights);
	}
}
