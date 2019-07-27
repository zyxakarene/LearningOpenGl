package zyx.opengl.shaders;

import java.util.HashMap;
import zyx.opengl.shaders.implementations.*;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IUpdateable;

public class ShaderManager implements IUpdateable
{

	private static final ShaderManager INSTANCE = new ShaderManager();

	private final HashMap<Shader, AbstractShader> shaderMap;
	private final AbstractShader[] shaderArray;

	private final Shader[] shaders;

	public static ShaderManager getInstance()
	{
		return INSTANCE;
	}
	
	private ShaderManager()
	{
		shaders = Shader.values();
		shaderMap = new HashMap<>();
		shaderArray = new AbstractShader[shaders.length];
	}

	public void initialize()
	{
		shaderMap.put(Shader.DEPTH, new DepthShader(AbstractShader.LOCK));
		shaderMap.put(Shader.WORLD, new WorldShader(AbstractShader.LOCK));
		shaderMap.put(Shader.SKYBOX, new SkyboxShader(AbstractShader.LOCK));
		shaderMap.put(Shader.SCREEN, new ScreenShader(AbstractShader.LOCK));
		shaderMap.put(Shader.PARTICLE, new ParticleShader(AbstractShader.LOCK));
		shaderMap.put(Shader.MESH_BATCH, new MeshBatchShader(AbstractShader.LOCK));
		shaderMap.put(Shader.WORLD_PARTICLE, new WorldParticleShader(AbstractShader.LOCK));
		shaderMap.put(Shader.MESH_BATCH_DEPTH, new MeshBatchDepthShader(AbstractShader.LOCK));
		shaderMap.put(Shader.AMBIENT_OCCLUSION, new AmbientOcclusionShader(AbstractShader.LOCK));
		shaderMap.put(Shader.DEFERED_LIGHT_PASS, new LightingPassShader(AbstractShader.LOCK));

		AbstractShader shader;
		int length = shaders.length;
		for (int i = 0; i < length; i++)
		{
			shader = shaderMap.get(shaders[i]);
			String shaderName;
			if (shader != null)
			{
				shaderName = shader.getName();
			}
			else
			{
				shaderName = shaders[i].toString();
			}
			Print.out("Loading shader:", shaderName);
			
			if (shader != null)
			{
				shader.load();
				shaderArray[i] = shader;
			}
		}
	}

	public String getNameFromProgram(int program)
	{
		for (AbstractShader shader : shaderArray)
		{
			if (shader.program == program)
			{
				return shader.getName();
			}
		}

		return "N/A";
	}

	public void bind(Shader shader)
	{
		shaderMap.get(shader).bind();
	}

	public <T extends AbstractShader> T get(Shader shader)
	{
		AbstractShader abstractShader = shaderMap.get(shader);
		
		return (T) abstractShader;
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		int len = shaderArray.length;
		for (int i = 0; i < len; i++)
		{
			shaderArray[i].update(timestamp, elapsedTime);
		}
	}
}
