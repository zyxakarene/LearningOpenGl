package zyx.opengl.shaders;

import java.util.HashMap;
import zyx.opengl.shaders.blocks.ShaderBlock;
import zyx.opengl.shaders.blocks.ShaderMatricesBlock;
import zyx.opengl.shaders.implementations.*;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IUpdateable;

public class ShaderManager implements IUpdateable
{

	private static final ShaderManager INSTANCE = new ShaderManager();

	private final HashMap<ShaderBlock, AbstractShaderBlock> shaderBlockMap;
	private final HashMap<Shader, AbstractShader> shaderMap;
	private final AbstractShader[] shaderArray;
	private final AbstractShaderBlock[] blockArray;

	private final Shader[] shaders;
	private final ShaderBlock[] blocks;

	public static ShaderManager getInstance()
	{
		return INSTANCE;
	}
	
	private ShaderManager()
	{
		shaders = Shader.values();
		blocks = ShaderBlock.values();
		
		shaderMap = new HashMap<>();
		shaderBlockMap = new HashMap<>();
		shaderArray = new AbstractShader[shaders.length];
		blockArray = new AbstractShaderBlock[blocks.length];
	}

	public void initialize()
	{
		shaderBlockMap.put(ShaderBlock.MATRICES, new ShaderMatricesBlock(AbstractShaderBlock.LOCK));
		
		shaderMap.put(Shader.DRAW, new DrawShader(AbstractShader.LOCK));
		shaderMap.put(Shader.DEPTH_1, new DepthShader(AbstractShader.LOCK, 1));
		shaderMap.put(Shader.DEPTH_2, new DepthShader(AbstractShader.LOCK, 2));
		shaderMap.put(Shader.DEPTH_3, new DepthShader(AbstractShader.LOCK, 3));
		shaderMap.put(Shader.DEPTH_4, new DepthShader(AbstractShader.LOCK, 4));
		shaderMap.put(Shader.WORLD_1, new WorldShader(AbstractShader.LOCK, 1));
		shaderMap.put(Shader.WORLD_2, new WorldShader(AbstractShader.LOCK, 2));
		shaderMap.put(Shader.WORLD_3, new WorldShader(AbstractShader.LOCK, 3));
		shaderMap.put(Shader.WORLD_4, new WorldShader(AbstractShader.LOCK, 4));
		shaderMap.put(Shader.SKYBOX, new SkyboxShader(AbstractShader.LOCK));
		shaderMap.put(Shader.SCREEN, new ScreenShader(AbstractShader.LOCK));
		shaderMap.put(Shader.PARTICLE, new ParticleShader(AbstractShader.LOCK));
		shaderMap.put(Shader.MESH_BATCH, new MeshBatchShader(AbstractShader.LOCK));
		shaderMap.put(Shader.WORLD_PARTICLE, new WorldParticleShader(AbstractShader.LOCK));
		shaderMap.put(Shader.WORLD_FORWARD_1, new WorldForwardShader(AbstractShader.LOCK, 1));
		shaderMap.put(Shader.WORLD_FORWARD_2, new WorldForwardShader(AbstractShader.LOCK, 2));
		shaderMap.put(Shader.WORLD_FORWARD_3, new WorldForwardShader(AbstractShader.LOCK, 3));
		shaderMap.put(Shader.WORLD_FORWARD_4, new WorldForwardShader(AbstractShader.LOCK, 4));
		shaderMap.put(Shader.MESH_BATCH_DEPTH, new MeshBatchDepthShader(AbstractShader.LOCK));
		shaderMap.put(Shader.AMBIENT_OCCLUSION, new AmbientOcclusionShader(AbstractShader.LOCK));
		shaderMap.put(Shader.MESH_BATCH_FORWARD, new MeshBatchForwardShader(AbstractShader.LOCK));
		shaderMap.put(Shader.DEFERED_LIGHT_PASS, new LightingPassShader(AbstractShader.LOCK));

		AbstractShaderBlock block;
		int blockLen = blocks.length;
		for (int i = 0; i < blockLen; i++)
		{
			block = shaderBlockMap.get(blocks[i]);
			
			if (block != null)
			{
				Print.out("Building shaderBlock:", blocks[i]);
				block.build();
				blockArray[i] = block;
			}
		}
		
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

	public <T extends AbstractShaderBlock> T get(ShaderBlock shader)
	{
		AbstractShaderBlock abstractBlock = shaderBlockMap.get(shader);
		
		return (T) abstractBlock;
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (AbstractShader shader : shaderArray)
		{
			shader.update(timestamp, elapsedTime);
		}
	}

	public void draw()
	{
		for (AbstractShaderBlock block : blockArray)
		{
			block.upload();
		}
	}
}
