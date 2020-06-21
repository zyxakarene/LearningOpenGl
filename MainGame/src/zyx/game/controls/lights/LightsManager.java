package zyx.game.controls.lights;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.opengl.lighs.ILight;
import zyx.opengl.lighs.ISun;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.DepthShader;
import zyx.opengl.shaders.implementations.LightingPassShader;
import zyx.opengl.shaders.implementations.MeshBatchDepthShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.GameConstants;

public class LightsManager
{

	private static LightsManager instane = new LightsManager();

	public static LightsManager getInstane()
	{
		return instane;
	}

	private ArrayList<ILight> allLights;
	private ILight[] nearestLights;
	private ISun currentSun;
	private int nearestLightCount;

	private WorldObject viewer;
	private LightSorter lightSorter;
	private LightingPassShader lightShader;
	private DepthShader[] depthShaders;
	private MeshBatchDepthShader batchedDepthShader;
	
	private Vector3f sunDir;

	private LightsManager()
	{
		allLights = new ArrayList<>();
		lightSorter = new LightSorter();

		nearestLightCount = GameConstants.LIGHT_COUNT;
		nearestLights = new ILight[nearestLightCount];
		
		sunDir = new Vector3f();
	}

	public void setSource(WorldObject viewer)
	{
		this.viewer = viewer;

		lightSorter.setViewer(viewer);
		lightShader = ShaderManager.getInstance().<LightingPassShader>get(Shader.DEFERED_LIGHT_PASS);
		batchedDepthShader = ShaderManager.getInstance().<MeshBatchDepthShader>get(Shader.MESH_BATCH_DEPTH);
		
		depthShaders = new DepthShader[4];
		depthShaders[0] = ShaderManager.getInstance().<DepthShader>get(Shader.DEPTH_1);
		depthShaders[1] = ShaderManager.getInstance().<DepthShader>get(Shader.DEPTH_2);
		depthShaders[2] = ShaderManager.getInstance().<DepthShader>get(Shader.DEPTH_3);
		depthShaders[3] = ShaderManager.getInstance().<DepthShader>get(Shader.DEPTH_4);
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

	public void setSun(ISun currentSun)
	{
		this.currentSun = currentSun;
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
			}
			else
			{
				nearestLights[i] = null;
			}
		}

		lightShader.uploadLights(nearestLights);
		
		if (currentSun != null)
		{
			currentSun.calculateShadows();
			
			currentSun.getSunDirection(sunDir);
			lightShader.uploadLightDirection(sunDir);
			lightShader.uploadSunMatrix();
			
			batchedDepthShader.uploadSunMatrix();
			for (DepthShader depthShader : depthShaders)
			{
				depthShader.uploadSunMatrix();
			}
		}
	}
}
